package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.quickstep.support.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public class ContentBuilder
{
   public ComponentFactory getComponentFactory()
   {
      return getDefaultComponentFactory();
   }

   public CellSpec getWindowContentSpec()
   {
      return spec().withAnchor(A.BOTH).withWeight(1.0).withInset(5);
   }

   protected <C extends Container> void genericBuildContent(C container, AbstractComponentCommand command, CellSpec spec)
   {
      container.setLayout(new GridBagLayout());
      JComponent component = command.getComponent(Direction.LEFT_TO_RIGHT, getComponentFactory());
      component = GridBagBuilder.applyPreferredSize(component, spec);

      GridBagConstraints constraints = spec.toConstraints(0, 0);
      container.add(component, constraints);
      DebugSupport.attachDebugInfo(component, container, constraints);
      DebugSupport.colorize(container, container.getComponents());
   }

   public void buildContent(Window window, AbstractComponentCommand command)
   {
      genericBuildContent(window, command, getWindowContentSpec().overrideWith(command.getSpec(Direction.LEFT_TO_RIGHT)));
      window.pack();
   }

   public void buildContent(JComponent container, AbstractComponentCommand command)
   {
      genericBuildContent(container, command, command.getSpec(Direction.LEFT_TO_RIGHT));
   }

   public JPanel buildContent(AbstractComponentCommand command)
   {
      JPanel panel = getComponentFactory().createPanel();
      genericBuildContent(panel, command, command.getSpec(Direction.LEFT_TO_RIGHT));
      return panel;
   }
}
