package org.quickstep;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.swing.*;

import org.quickstep.support.DebugSupport;

public final class GridBagToolKit
{
   public static enum A
   {
      CENTER, BOTH
   }

   public static enum AX
   {
      CENTER, LEFT, RIGHT, BOTH
   }

   public static enum AY
   {
      CENTER, TOP, BOTTOM, BOTH
   }

   public enum Orientation
   {
      HORIZONTAL, VERTICAL;

      public boolean isHorizontal()
      {
         return this == HORIZONTAL;
      }

      public Orientation getOther()
      {
         return this == HORIZONTAL ? VERTICAL : HORIZONTAL;
      }
   }

   public static Logger logger = Logger.getLogger(GridBagBuilder.class.getName());

   private static ComponentFactory defaultComponentFactory = new ComponentFactory();

   public static ComponentFactory getDefaultComponentFactory()
   {
      return defaultComponentFactory;
   }

   private GridBagToolKit()
   {
   }


   public static CellSpec spec()
   {
      return new CellSpec();
   }

   public static CellSpec specWithFillX()
   {
      return spec().withAnchorX(AX.BOTH).withWeightX(1.0);
   }

   public static CellSpec specWithFillY()
   {
      return spec().withAnchorY(AY.BOTH).withWeightY(1.0);
   }

   public static CellSpec specWithFill()
   {
      return spec().withAnchor(AX.BOTH, AY.BOTH).withWeight(1.0, 1.0);
   }


   public static CellSpec completeSpec()
   {
      return new CellSpec(null, null, null, null, 1, 1, 0.0, 0.0, AX.CENTER, AY.CENTER, false, 0, 0, 0, 0, 0, 0);
   }


   public static GridSpec gridSpec()
   {
      return new GridSpec();
   }

   public static LineSpec lineSpec()
   {
      return new LineSpec();
   }


   public static PanelCommand panel()
   {
      return new PanelCommand();
   }

   public static PanelCommand vpanel()
   {
      return new PanelCommand().withOrientation(Orientation.VERTICAL);
   }

   public static LineCommand line()
   {
      return new LineCommand();
   }

   public static SeqCommand seq()
   {
      return new SeqCommand();
   }

   public static SeqCommand seq(JComponent... components)
   {
      return seq().addAll(Arrays.asList(components));
   }

   public static ComponentCommand component(JComponent component)
   {
      return new ComponentCommand(component);
   }


   public static Window buildContent(Window window, AbstractComponentCommand command)
   {
      return getDefaultComponentFactory().buildContent(window, command);
   }

   public static JComponent buildContent(JComponent component, AbstractComponentCommand command)
   {
      return getDefaultComponentFactory().buildContent(component, command);
   }

   public static JPanel buildContent(ComponentCommand command)
   {
      return getDefaultComponentFactory().build(command);
   }


   public static void debug()
   {
      debug(true);
   }

   public static void debug(boolean value)
   {
      DebugSupport.debug(value);
   }


   public static class ResizablePanel extends JPanel
   {
      public ResizablePanel(JComponent component)
      {
         super(new BorderLayout());
         add(component);
      }

      @Override
      public Dimension getMinimumSize()
      {
         return getPreferredSize();
      }

      @Override
      public int getBaseline(int width, int height)
      {
         return getComponent(0).getBaseline(width, height);
      }

      @Override
      public BaselineResizeBehavior getBaselineResizeBehavior()
      {
         return getComponent(0).getBaselineResizeBehavior();
      }
   }
}
