package org.quickstep.command;

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


import javax.swing.*;

import org.quickstep.ComponentFactory;
import org.quickstep.GridBagToolKit;

public class LabelCommand extends AbstractComponentCommand<LabelCommand>
{
   private String text;

   public LabelCommand(String text)
   {
      this.text = text;
   }

   @Override
   public JComponent getComponent(GridBagToolKit.Direction parentDirection, ComponentFactory parentFactory)
   {
      return parentFactory.createLabel(text);
   }
}
