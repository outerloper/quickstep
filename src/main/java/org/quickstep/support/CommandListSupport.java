package org.quickstep.support;

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


import java.util.*;
import javax.swing.*;

import org.quickstep.command.*;
import org.quickstep.spec.CellSpec;

import static org.quickstep.GridBagToolKit.*;

public class CommandListSupport<T> implements Iterable<GridBagCommand>
{
   private final T owner;
   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();

   public CommandListSupport(T owner)
   {
      this.owner = owner;
   }

   public T addLineBreak()
   {
      return add(new LineBreakCommand());
   }

   public T addBlank()
   {
      return addBlank(spec());
   }

   public T addBlank(CellSpec spec)
   {
      return add((String) null, spec);
   }

   public T add(String text)
   {
      return add(text, spec());
   }

   public T add(String text, CellSpec spec)
   {
      return add(new LabelCommand(text).withSpec(spec));
   }

   public T add(String label, JComponent component)
   {
      add(label);
      return add(component);
   }

   public T add(String label, JComponent component, CellSpec spec)
   {
      add(label);
      return add(component, spec);
   }

   public T add(String label, GridBagCommand command)
   {
      add(label);
      return add(command);
   }

   public T add(JComponent component)
   {
      return add(component, spec());
   }

   public T add(JComponent component, CellSpec spec)
   {
      return add(component(component).withSpec(spec));
   }

   public T add(Iterable<? extends JComponent> components)
   {
      return add(components, spec());
   }

   public T add(Iterable<? extends JComponent> components, CellSpec spec)
   {
      for (JComponent component : components)
      {
         add(component, spec);
      }
      return owner;
   }

   public T addHeader(String title)
   {
      return add(new HeaderCommand(title));
   }

   public T addSeparator()
   {
      return add(new SeparatorCommand());
   }

   public T addLineSeparator()
   {
      return add(new LineSeparatorCommand());
   }

   public T add(GridBagCommand command)
   {
      if (command == null)
      {
         throw new NullPointerException();
      }
      commands.add(command);
      return owner;
   }

   @Override
   public Iterator<GridBagCommand> iterator()
   {
      return commands.iterator();
   }
}
