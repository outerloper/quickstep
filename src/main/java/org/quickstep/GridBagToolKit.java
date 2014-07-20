package org.quickstep;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.quickstep.util.CustomToolTipSupport;

public final class GridBagToolKit
{
   public enum AnchorX
   {
      LEFT, CENTER, RIGHT
   }

   public enum AnchorY
   {
      TOP, MIDDLE, BOTTOM
   }

   public enum Orientation
   {
      HORIZONTAL, VERTICAL
   }

   private static boolean debug = false;
   private static CustomToolTipSupport toolTipSupportForDebugging = new CustomToolTipSupport();

   public static Logger logger = Logger.getLogger(GridBagBuilder.class.getName());


   private GridBagToolKit()
   {
   }


   public static GridBagSpec spec()
   {
      return new GridBagSpec();
   }

   public static GridBagSpec growX()
   {
      return spec().withFillX().withWeightX(1.0);
   }

   public static GridBagSpec growY()
   {
      return spec().withFillY().withWeightY(1.0);
   }

   public static GridBagSpec grow()
   {
      return spec().withFill().withWeight(1.0, 1.0);
   }


   public static GridBagSpec completeSpec()
   {
      return new GridBagSpec(null, null, 1, 1, 0.0, 0.0, AnchorX.CENTER, AnchorY.MIDDLE, false, false, 0, 0, 0, 0, 0, 0);
   }


   public static GridBagBuilder panel()
   {
      return new GridBagBuilder(new ResizablePanel());
   }

   public static GridBagBuilder panel(JPanel panel)
   {
      return new GridBagBuilder(panel);
   }


   public static GridBagLine line()
   {
      return new GridBagLine();
   }


   private static <C extends Container> C genericBuildContent(C container, ComponentBuilder builder)
   {
      container.setLayout(new GridBagLayout());
      JComponent component = builder.build();
      GridBagConstraints constraints = grow().withInset(5).overrideWith(builder.getSpec()).toConstraints(0, 0);

      container.add(component, constraints);
      attachDebugInfo(component, container, constraints);
      return container;
   }

   public static Window buildContent(Window window, ComponentBuilder builder)
   {
      Window result = genericBuildContent(window, builder);
      window.pack();
      return result;
   }

   public static JPanel buildContent(JPanel panel, ComponentBuilder builder)
   {
      return genericBuildContent(panel, builder);
   }

   public static JPanel buildContent(ComponentBuilder builder)
   {
      return genericBuildContent(new JPanel(), builder);
   }


   public static void debug()
   {
      debug(true);
   }

   public static void debug(boolean value)
   {
      debug = value;
      toolTipSupportForDebugging = new CustomToolTipSupport();
      toolTipSupportForDebugging.setCustomHideDelay(300);
   }

   public static String gbcToString(GridBagConstraints c)
   {
      return String.format("GBC{grid=%s,%s gridsize=%s,%s weight=%s,%s anchor=%s fill=%s " +
                              "insets(top=%s left=%s bottom=%s right=%s) ipad=%s,%s}",
                           c.gridx, c.gridy, c.gridwidth, c.gridheight, c.weightx, c.weighty,
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


   static class ResizablePanel extends JPanel
   {
      public ResizablePanel()
      {
         super(new BorderLayout());
      }

      @Override
      public Dimension getMinimumSize()
      {
         return getPreferredSize();
      }
   }

   static void attachDebugInfo(JComponent component, Container container, GridBagConstraints constraints)
   {
      if (!debug)
      {
         return;
      }
      if (component instanceof JPanel)
      {
         component.setBackground(new Color((int) (Math.random() * 0xFFFFFF)));
      }
      String containerId = objectId(container);
      String componentId = objectId(component);
      String gbcString = gbcToString(constraints);
      logger.log(Level.INFO, String.format("%s.add(%s, %s)%n", containerId, componentId, gbcString));
      if (component != null)
      {
         component.setToolTipText(String.format("<html>this: <b>%s</b><br>parent: <b>%s</b><br>%s</html>", componentId, containerId, gbcString));
         toolTipSupportForDebugging.addComponent(component);
      }
   }

   static String objectId(Object object)
   {
      return object == null ? "null" : object.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(object));
   }
}
