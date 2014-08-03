package org.quickstep;

import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

import org.quickstep.util.DebugSupport;

public final class GridBagToolKit
{

   public static final Fill FILL_NONE = Fill.NONE;
   public static final Fill FILL_X = Fill.X;
   public static final Fill FILL_Y = Fill.Y;
   public static final Fill FILL_BOTH = Fill.BOTH;

   public static final AnchorX X_CENTER = AnchorX.CENTER;
   public static final AnchorX X_LEFT = AnchorX.LEFT;
   public static final AnchorX X_RIGHT = AnchorX.RIGHT;
   public static final AnchorX X_BOTH = AnchorX.BOTH;

   public static final AnchorY Y_CENTER = AnchorY.CENTER;
   public static final AnchorY Y_TOP = AnchorY.TOP;
   public static final AnchorY Y_BOTTOM = AnchorY.BOTTOM;
   public static final AnchorY Y_BOTH = AnchorY.BOTH;

   private interface AX {}
   private interface AY {}

   public static enum Fill implements AX, AY
   {
      NONE, X, Y, BOTH
   }

   public static enum AnchorX implements AX
   {
      CENTER, LEFT, RIGHT, BOTH
   }

   public static enum AnchorY implements AY
   {
      CENTER, TOP, BOTTOM, BOTH
   }

   public enum Orientation
   {
      HORIZONTAL, VERTICAL
   }

   public static Logger logger = Logger.getLogger(GridBagBuilder.class.getName());


   private GridBagToolKit()
   {
   }


   public static CellSpec spec()
   {
      return new CellSpec();
   }

   public static CellSpec spec(Fill fill)
   {
      switch (fill)
      {
         case BOTH:
            return grow();
         case X:
            return growX();
         case Y:
            return growY();
      }
      return spec();
   }

   public static CellSpec growX()
   {
      return spec().withAnchorX(X_BOTH).withWeightX(1.0);
   }

   public static CellSpec growY()
   {
      return spec().withAnchorY(Y_BOTH).withWeightY(1.0);
   }

   public static CellSpec grow()
   {
      return spec().withAnchor(X_BOTH, Y_BOTH).withWeight(1.0, 1.0);
   }


   public static CellSpec completeSpec()
   {
      return new CellSpec(null, null, 1, 1, 0.0, 0.0, AnchorX.CENTER, AnchorY.CENTER, 0, 0, 0, 0, 0, 0);
   }


   public static PanelCommand panel()
   {
      return new PanelCommand(new ResizablePanel());
   }

   public static PanelCommand panel(JPanel panel)
   {
      return new PanelCommand(panel);
   }

   public static LineCommand line()
   {
      return new LineCommand();
   }

   public static SeqCommand seq()
   {
      return new SeqCommand();
   }


   private static <C extends Container> C genericBuildContent(C container, CellCommand command)
   {
      container.setLayout(new GridBagLayout());
      JComponent component = command.getComponent();
      GridBagConstraints constraints = grow().withInset(5).overrideWith(command.getSpec()).toConstraints(0, 0);

      container.add(component, constraints);
      DebugSupport.attachDebugInfo(component, container, constraints);
      DebugSupport.colorize(container, container.getComponents());
      return container;
   }

   public static Window buildContent(Window window, CellCommand command)
   {
      Window result = genericBuildContent(window, command);
      window.pack();
      return result;
   }

   public static JPanel buildContent(JPanel panel, CellCommand command)
   {
      return genericBuildContent(panel, command);
   }

   public static JPanel buildContent(ComponentCommand command)
   {
      return genericBuildContent(new JPanel(), command);
   }


   public static void debug()
   {
      debug(true);
   }

   public static void debug(boolean value)
   {
      DebugSupport.debug(value);
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
}
