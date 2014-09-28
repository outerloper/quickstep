package org.quickstep;

import java.awt.*;
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

   public enum Direction
   {
      LEFT_TO_RIGHT, TOP_TO_BOTTOM;

      public boolean isLeftToRight()
      {
         return this == LEFT_TO_RIGHT;
      }

      public Direction getOther()
      {
         return this == LEFT_TO_RIGHT ? TOP_TO_BOTTOM : LEFT_TO_RIGHT;
      }
   }

   public static Logger logger = Logger.getLogger(GridBagBuilder.class.getName());

   private static ComponentFactory defaultComponentFactory = new ComponentFactory();
   private static ContentBuilder defaultContentBuilder = new ContentBuilder();

   public static ComponentFactory getDefaultComponentFactory()
   {
      return defaultComponentFactory;
   }

   public static ContentBuilder getDefaultContentBuilder()
   {
      return defaultContentBuilder;
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
      return spec().withAnchor(A.BOTH).withWeight(1.0);
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
      return new PanelCommand().withDirection(Direction.TOP_TO_BOTTOM);
   }

   public static LineCommand line()
   {
      return new LineCommand();
   }

   public static SeqCommand seq()
   {
      return new SeqCommand();
   }

   public static ComponentCommand component(JComponent component)
   {
      return new ComponentCommand(component);
   }

   public static LabelCommand label(String text)
   {
      return new LabelCommand(text);
   }

   public static HeaderCommand header(String text)
   {
      return new HeaderCommand(text);
   }

   public static SeparatorCommand separator()
   {
      return new SeparatorCommand();
   }

   public static LineSeparatorCommand lineSeparator()
   {
      return new LineSeparatorCommand();
   }


   public static void buildContent(Window window, AbstractComponentCommand command)
   {
      getDefaultContentBuilder().buildContent(window, command);
   }

   public static void buildContent(JComponent container, AbstractComponentCommand command)
   {
      getDefaultContentBuilder().buildContent(container, command);
   }

   public static JPanel buildContent(AbstractComponentCommand command)
   {
      return getDefaultContentBuilder().buildContent(command);
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
      public static ResizablePanel wrap(JComponent component)
      {
         ResizablePanel panel = new ResizablePanel();
         panel.add(component);
         return panel;
      }

      public ResizablePanel()
      {
         super(new BorderLayout());
      }

      @Override
      public Dimension getMinimumSize()
      {
         return getPreferredSize();
      }

      @Override
      public int getBaseline(int width, int height)
      {
         if (getComponentCount() > 0)
         {
            int result;
            Component firstComponent = getComponent(0);
            Dimension size = firstComponent.getPreferredSize();
            result = firstComponent.getLocation().y + firstComponent.getBaseline(size.width, size.height);

            if (getLayout() instanceof GridBagLayout)
            {
               GridBagLayout layout = (GridBagLayout) getLayout();
               GridBagConstraints constraints = layout.getConstraints(firstComponent);
               if (!isValid())
               {
                  result += constraints.insets.top;
               }
               result += constraints.ipady / 2;
            }

            return result;
         }
         return -1;
      }

      @Override
      public BaselineResizeBehavior getBaselineResizeBehavior()
      {
         if (getComponentCount() > 0)
         {
            return getComponent(0).getBaselineResizeBehavior();
         }
         return BaselineResizeBehavior.OTHER;
      }
   }
}
