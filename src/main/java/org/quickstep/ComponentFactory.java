package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.support.DebugSupport;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class ComponentFactory
{
   public JLabel createLabel(String text)
   {
      return new JLabel(text);
   }

   public JComponent createHeader(String title, boolean placedInFirstRow)
   {
      GridContainerCommand result = panel().add(title).add(createSeparator(Orientation.HORIZONTAL), specWithFillX());
      if (!placedInFirstRow)
      {
         result.withRow(0, spec().withInsetTop(5));
      }
      return result.getComponent();
   }

   public JComponent createSeparator(Orientation orientation)
   {
      return new JSeparator(orientation.isHorizontal() ? JSeparator.HORIZONTAL : JSeparator.VERTICAL);
   }

   public JPanel createPanel()
   {
      return new JPanel();
   }

   public Border createBorder(String title)
   {
      return createCompoundBorder(createTitledBorder(title), createEmptyBorder(5, 5, 5, 5));
   }

   public JScrollPane createScrollPane()
   {
      JScrollPane result = new JScrollPane();
      result.getHorizontalScrollBar().setUnitIncrement(10);
      result.getHorizontalScrollBar().setBlockIncrement(10);
      result.getVerticalScrollBar().setUnitIncrement(10);
      result.getVerticalScrollBar().setBlockIncrement(10);
      result.setBorder(createEmptyBorder());
      return result;
   }

   public GridSpec createDefaultGridSpec()
   {
      return new GridSpec()
         .withDefault(spec().withGap(5).withAnchorX(AX.LEFT).withBaseline(true))
         .withRow(0, spec().withInsetTop(0))
         .withColumn(0, spec().withInsetLeft(0));
   }

   public AX getContentAnchorX()
   {
      return AX.CENTER;
   }

   public AY getContentAnchorY()
   {
      return AY.CENTER;
   }

   public ComponentFactory getContentFactory()
   {
      return this;
   }


   private <C extends Container> C genericBuildContent(C container, AbstractComponentCommand command)
   {
      container.setLayout(new GridBagLayout());
      JComponent component = command.getComponent(Orientation.HORIZONTAL, this);
      GridBagConstraints constraints = specWithFill().withInset(5).overrideWith(command.getDefaultSpec(Orientation.HORIZONTAL)).toConstraints(0, 0);

      container.add(component, constraints);
      DebugSupport.attachDebugInfo(component, container, constraints);
      DebugSupport.colorize(container, container.getComponents());
      return container;
   }

   public Window buildContent(Window window, AbstractComponentCommand command)
   {
      Window result = genericBuildContent(window, command);
      window.pack();
      return result;
   }

   public JComponent buildContent(JComponent component, AbstractComponentCommand command)
   {
      return genericBuildContent(component, command);
   }

   public JPanel build(ComponentCommand command)
   {
      return genericBuildContent(createPanel(), command);
   }
}
