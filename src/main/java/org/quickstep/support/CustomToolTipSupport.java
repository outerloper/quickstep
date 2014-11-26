package org.quickstep.support;

/*
 * ---------------------------------------------------------------------
 * Quickstep
 * ------
 * Copyright (C) 2014 Konrad Sacala
 * ------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * ---------------------------------------------------------------------
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.google.common.base.Strings;

public class CustomToolTipSupport
{
   public static final String CUSTOM_TOOLTIP_TEXT = "CustomToolTipText";
   public static final String TOOL_TIP_BACKGROUND = "ToolTip.background";
   public static final String TOOL_TIP_FONT = "ToolTip.font";

   private final Timer showToolTipTimer;
   private final ToolTipManager tooltipManager = ToolTipManager.sharedInstance();
   private final MouseEvent fakeMouseEventForHidingToolTip = new MouseEvent(new JLabel(), MouseEvent.MOUSE_MOVED, 0, 0, 0, 0, 0, false);

   private Color originalBackgroundColor = UIManager.getColor(TOOL_TIP_BACKGROUND);
   private Font originalFont = UIManager.getFont(TOOL_TIP_FONT);
   private int originalInitialDelay = tooltipManager.getInitialDelay();
   private int originalDismissDelay = tooltipManager.getDismissDelay();

   private boolean enabled = true;
   private Color customBackgroundColor = originalBackgroundColor;
   private Font customFont = originalFont;
   private int customHideDelay = 0;

   private MouseEvent mouseEvent;
   private Object target;
   private long toolTipHiddenTimestamp;
   private ToolTipTextProvider toolTipTextProvider = new ToolTipTextProvider()
   {
      @Override
      public String getToolTipText(Object component)
      {
         if (component instanceof JComponent)
         {
            return (String) ((JComponent) component).getClientProperty(CUSTOM_TOOLTIP_TEXT);
         }
         return null;
      }
   };

   private MouseAdapter mouseAdapter = new CustomToolTipMouseAdapter();

   private class CustomToolTipMouseAdapter extends MouseAdapter
   {
      @Override
      public void mouseEntered(MouseEvent e)
      {
         reset(e.getSource());
      }

      @Override
      public void mouseExited(MouseEvent e)
      {
         reset(e.getSource());
      }

      @Override
      public void mouseMoved(MouseEvent e)
      {
         setTarget(e.getSource(), e);
      }

      private void reset(Object object)
      {
         resetTarget();

         if (object instanceof JComponent)
         {
            JComponent component = (JComponent) object;
            component.setToolTipText(null);
         }
      }
   }

   public void addComponent(JComponent component)
   {
      String toolTipText = component.getToolTipText();
      if (toolTipText != null)
      {
         component.putClientProperty(CUSTOM_TOOLTIP_TEXT, toolTipText);
         component.setToolTipText(null);
      }
      component.addMouseMotionListener(mouseAdapter);
      component.addMouseListener(mouseAdapter);
   }

   public CustomToolTipSupport()
   {
      showToolTipTimer = new Timer(getCustomHideDelay(), new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (target instanceof JComponent && !Strings.isNullOrEmpty(getToolTip()))
            {
               ((JComponent) target).setToolTipText(getToolTip());
            }
            imitateMouseMove();
         }
      });
      showToolTipTimer.setRepeats(false);
   }

   public static interface ToolTipTextProvider
   {
      public String getToolTipText(Object target);
   }

   public void setToolTipTextProvider(ToolTipTextProvider toolTipTextProvider)
   {
      this.toolTipTextProvider = toolTipTextProvider;
   }

   public String getToolTip()
   {
      return isToolTipHidden() ? null : toolTipTextProvider.getToolTipText(target);
   }

   private int getCustomHideDelay()
   {
      return customHideDelay;
   }

   public void setCustomBackgroundColor(Color customBackgroundColor)
   {
      this.customBackgroundColor = customBackgroundColor;
   }

   public void setCustomFont(Font customFont)
   {
      this.customFont = customFont;
   }

   public void setCustomHideDelay(int customHideDelay)
   {
      this.customHideDelay = customHideDelay;
   }

   public Font getCustomFont()
   {
      return customFont;
   }

   private void customizeToolTipSettings()
   {
      setToolTipSettings(customBackgroundColor, customFont, 10, 100000);
   }

   private void restoreOriginalToolTipSettings()
   {
      setToolTipSettings(originalBackgroundColor, originalFont, originalInitialDelay, originalDismissDelay);
   }

   private void setToolTipSettings(Color color, Font font, int initialDelay, int dismissDelay)
   {
      UIManager.put(TOOL_TIP_BACKGROUND, color);
      UIManager.put(TOOL_TIP_FONT, font);
      tooltipManager.setInitialDelay(initialDelay);
      tooltipManager.setDismissDelay(dismissDelay);
   }

   private void imitateMouseMove()
   {
      if (mouseEvent != null && mouseEvent.getSource() != null)
      {
         Container component = (Container) mouseEvent.getSource();
         int x = mouseEvent.getX();
         int y = mouseEvent.getY();
         for (int i = 0; i < 2; i++)
         {
            component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_MOVED, 0, 0, x, y, 0, false));
         }
      }
   }

   private void hideToolTipForCustomHideDelay()
   {
      toolTipHiddenTimestamp = System.currentTimeMillis();
      showToolTipTimer.setInitialDelay(getCustomHideDelay());
      showToolTipTimer.restart();
   }

   private boolean isToolTipHidden()
   {
      return !enabled || System.currentTimeMillis() - toolTipHiddenTimestamp < getCustomHideDelay();
   }

   public boolean isEnabled()
   {
      return enabled;
   }

   public void setEnabled(boolean enabled)
   {
      this.enabled = enabled;
   }

   public Object getTarget()
   {
      return target;
   }

   public void setTarget(Object component, MouseEvent event)
   {
      if (target != component)
      {
         target = component;
         customizeToolTipSettings();
         hideToolTipForCustomHideDelay();
         toolTipHiddenTimestamp = System.currentTimeMillis();
      }
      mouseEvent = event;
   }

   public void resetTarget()
   {
      restoreOriginalToolTipSettings();
      target = null;
      mouseEvent = null;
   }

   public void hideToolTip()
   {
      tooltipManager.mouseMoved(fakeMouseEventForHidingToolTip);
   }
}
