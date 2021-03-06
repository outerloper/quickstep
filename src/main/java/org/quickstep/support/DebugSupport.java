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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.GridBagToolKit;

public class DebugSupport
{
   private static Map<Color, Color> nextColors = new HashMap<Color, Color>();
   private static CustomToolTipSupport toolTipSupportForDebugging = new CustomToolTipSupport();

   private static boolean debug = false;

   public static void debug(boolean value)
   {
      debug = value;
      toolTipSupportForDebugging = new CustomToolTipSupport();
      toolTipSupportForDebugging.setCustomHideDelay(300);
   }

   public static void attachDebugInfo(JComponent component, Container container, GridBagConstraints constraints)
   {
      if (debug)
      {
         String containerId = objectId(container);
         String componentId = objectId(component);
         String gbcString = gbcToString(constraints);
         GridBagToolKit.logger.log(Level.INFO, String.format("%s.add(%s, %s)%n", containerId, componentId, gbcString));
         if (component != null)
         {
            component.setToolTipText(String.format("<html>this: <b>%s</b><br>parent: <b>%s</b><br>%s</html>", componentId, containerId, gbcString));
            toolTipSupportForDebugging.addComponent(component);
         }
      }
   }

   public static void colorize(Container parent, Component[] components)
   {
      if (debug && components != null)
      {
         for (Component component : components)
         {
            boolean opaque = false;
            if (component instanceof JComponent)
            {
               JComponent jComponent = (JComponent) component;
               opaque = jComponent.isOpaque();
               if (!opaque)
               {
                  jComponent.setOpaque(true);
               }
            }
            if (component instanceof JPanel || !opaque)
            {
               component.setBackground(getNextColor(parent.getBackground()));
            }
            if (component instanceof Container)
            {
               Container container = (Container) component;
               colorize(container, container.getComponents());
            }
         }
      }
   }

   private static Color getNextColor(Color color)
   {
      if (!nextColors.containsKey(color))
      {
         nextColors.put(color, new Color((int) (Math.random() * 0xFFFFFF)));
      }
      return nextColors.get(color);
   }


   public static String gbcToString(GridBagConstraints c)
   {
      return String.format("GBC{grid=%s,%s gridsize=%s,%s weight=%s,%s anchor=%s fill=%s " +
                              "insets(top=%s left=%s bottom=%s right=%s) ipad=%s,%s}",
                           gridToString(c.gridx), gridToString(c.gridy),
                           gridSizeToString(c.gridwidth), gridSizeToString(c.gridheight), c.weightx, c.weighty,
                           anchorToString(c.anchor), fillToString(c.fill),
                           c.insets.top, c.insets.left, c.insets.bottom, c.insets.right, c.ipadx, c.ipady);
   }

   public static boolean gbcEquals(GridBagConstraints c1, GridBagConstraints c2)
   {
      if (c1 == null || c2 == null)
      {
         return c1 == c2;
      }
      return c1.gridx == c2.gridx &&
         c1.gridy == c2.gridy &&
         c1.gridwidth == c2.gridwidth &&
         c1.gridheight == c2.gridheight &&
         c1.weightx == c2.weightx &&
         c1.weighty == c2.weighty &&
         c1.fill == c2.fill &&
         c1.anchor == c2.anchor &&
         c1.insets != null && c1.insets.equals(c2.insets) &&
         c1.ipadx == c2.ipadx &&
         c1.ipady == c2.ipady;
   }

   public static String gridToString(int size)
   {
      return size == GridBagConstraints.RELATIVE ? "RELATIVE" : Integer.toString(size);
   }

   public static String gridSizeToString(int size)
   {
      return size == GridBagConstraints.REMAINDER ? "REMAINDER" : Integer.toString(size);
   }

   public static String anchorToString(int anchorConstant)
   {
      switch (anchorConstant)
      {
         case GridBagConstraints.BASELINE:
            return "BASELINE";
         case GridBagConstraints.ABOVE_BASELINE:
            return "ABOVE_BASELINE";
         case GridBagConstraints.ABOVE_BASELINE_LEADING:
            return "ABOVE_BASELINE_LEADING";
         case GridBagConstraints.BASELINE_LEADING:
            return "BASELINE_LEADING";
         case GridBagConstraints.BELOW_BASELINE_LEADING:
            return "BELOW_BASELINE_LEADING";
         case GridBagConstraints.BELOW_BASELINE:
            return "BELOW_BASELINE";
         case GridBagConstraints.BELOW_BASELINE_TRAILING:
            return "BELOW_BASELINE_TRAILING";
         case GridBagConstraints.BASELINE_TRAILING:
            return "BASELINE_TRAILING";
         case GridBagConstraints.ABOVE_BASELINE_TRAILING:
            return "ABOVE_BASELINE_TRAILING";
         case GridBagConstraints.CENTER:
            return "CENTER";
         case GridBagConstraints.PAGE_START:
            return "PAGE_START";
         case GridBagConstraints.FIRST_LINE_START:
            return "FIRST_LINE_START";
         case GridBagConstraints.LINE_START:
            return "LINE_START";
         case GridBagConstraints.LAST_LINE_START:
            return "LAST_LINE_START";
         case GridBagConstraints.PAGE_END:
            return "PAGE_END";
         case GridBagConstraints.LAST_LINE_END:
            return "LAST_LINE_END";
         case GridBagConstraints.LINE_END:
            return "LINE_END";
         case GridBagConstraints.FIRST_LINE_END:
            return "FIRST_LINE_END";
         case GridBagConstraints.NORTH:
            return "NORTH";
         case GridBagConstraints.NORTHWEST:
            return "NORTHWEST";
         case GridBagConstraints.WEST:
            return "WEST";
         case GridBagConstraints.SOUTHWEST:
            return "SOUTHWEST";
         case GridBagConstraints.SOUTH:
            return "SOUTH";
         case GridBagConstraints.SOUTHEAST:
            return "SOUTHEAST";
         case GridBagConstraints.EAST:
            return "EAST";
         case GridBagConstraints.NORTHEAST:
            return "NORTHEAST";
         default:
            return "" + anchorConstant;
      }
   }

   public static String fillToString(int fillConstant)
   {
      switch (fillConstant)
      {
         case GridBagConstraints.NONE:
            return "NONE";
         case GridBagConstraints.HORIZONTAL:
            return "HORIZONTAL";
         case GridBagConstraints.VERTICAL:
            return "VERTICAL";
         case GridBagConstraints.BOTH:
            return "BOTH";
         default:
            return "" + fillConstant;
      }
   }

   public static String objectId(Object object)
   {
      return object == null ? "null" : object.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(object));
   }
}
