package org.quickstep;

import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

import org.quickstep.util.DebugSupport;

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
      return new CellSpec(null, null, 1, 1, 0.0, 0.0, AX.CENTER, AY.CENTER, 0, 0, 0, 0, 0, 0);
   }


   public static PanelCommand panel()
   {
      return new PanelCommand();
   }

   public static PanelCommand verticalPanel()
   {
      return panel().withOrientation(Orientation.VERTICAL);
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
      GridBagConstraints constraints = specWithFill().withInset(5).overrideWith(command.getSpec()).toConstraints(0, 0);

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
