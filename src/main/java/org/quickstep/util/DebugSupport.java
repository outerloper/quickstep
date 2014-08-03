package org.quickstep.util;

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
                           c.gridx, c.gridy, gridSizeToString(c.gridwidth), gridSizeToString(c.gridheight), c.weightx, c.weighty,
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

   public static String gridSizeToString(int size) // TODO test
   {
      return size == 0 ? "REMAINDER" : Integer.toString(size);
   }

   public static String anchorToString(int anchorConstant)
   {
      switch (anchorConstant)
      {
         case GridBagConstraints.CENTER:
            return "CENTER";
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
