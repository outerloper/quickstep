package org.quickstep;

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
import javax.swing.*;

import org.quickstep.command.AbstractComponentCommand;
import org.quickstep.spec.CellSpec;
import org.quickstep.support.DebugSupport;
import org.quickstep.support.SizeGroupsSupport;

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
      component = SizeGroupsSupport.applyPreferredSize(component, spec);

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
