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

import org.quickstep.spec.CellSpec;
import org.quickstep.support.CommandListSupport;

public class SeqCommand implements GridBagCommand
{
   private final CommandListSupport<SeqCommand> commandListSupport = new CommandListSupport<SeqCommand>(this);

   public SeqCommand()
   {
   }

   public SeqCommand addLineBreak()
   {
      return commandListSupport.addLineBreak();
   }

   public SeqCommand addBlank()
   {
      return commandListSupport.addBlank();
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public SeqCommand add(String label)
   {
      return commandListSupport.add(label);
   }

   public SeqCommand add(String label, CellSpec spec)
   {
      return commandListSupport.add(label, spec);
   }

   public SeqCommand add(String label, JComponent component)
   {
      return commandListSupport.add(label, component);
   }

   public SeqCommand add(String label, JComponent component, CellSpec spec)
   {
      return commandListSupport.add(label, component, spec);
   }

   public SeqCommand add(String label, GridBagCommand command)
   {
      return commandListSupport.add(label, command);
   }

   public SeqCommand add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public SeqCommand add(Iterable<? extends JComponent> components)
   {
      return commandListSupport.add(components);
   }

   public SeqCommand add(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.add(components, spec);
   }

   public SeqCommand addHeader(String title)
   {
      return commandListSupport.addHeader(title);
   }

   public SeqCommand addSeparator()
   {
      return commandListSupport.addSeparator();
   }

   public SeqCommand addLineSeparator()
   {
      return commandListSupport.addLineSeparator();
   }

   public SeqCommand add(GridBagCommand command)
   {
      return commandListSupport.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      for (GridBagCommand command : commandListSupport)
      {
         command.apply(builder);
      }
   }
}
