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


import java.util.logging.Level;

import javax.swing.*;

import org.quickstep.GridBagException;
import org.quickstep.spec.CellSpec;
import org.quickstep.spec.LineSpec;
import org.quickstep.support.CommandListSupport;
import org.quickstep.support.LineSpecSupport;

import static org.quickstep.GridBagToolKit.*;

public class LineCommand implements GridBagCommand
{
   private final LineSpecSupport<LineCommand> lineSpecSupport = new LineSpecSupport<LineCommand>(this);
   private final CommandListSupport<LineCommand> commandListSupport = new CommandListSupport<LineCommand>(this);

   public LineCommand()
   {
   }

   public LineCommand addBlank()
   {
      return commandListSupport.addBlank();
   }

   public LineCommand addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public LineCommand add(String label)
   {
      return commandListSupport.add(label);
   }

   public LineCommand add(String label, CellSpec spec)
   {
      return commandListSupport.add(label, spec);
   }

   public LineCommand add(String label, JComponent component)
   {
      return commandListSupport.add(label, component);
   }

   public LineCommand add(String label, JComponent component, CellSpec spec)
   {
      return commandListSupport.add(label, component, spec);
   }

   public LineCommand add(String label, GridBagCommand command)
   {
      return commandListSupport.add(label, command);
   }

   public LineCommand add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public LineCommand add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public LineCommand add(Iterable<? extends JComponent> components)
   {
      return commandListSupport.add(components);
   }

   public LineCommand add(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.add(components, spec);
   }

   public LineCommand addSeparator()
   {
      return commandListSupport.addSeparator();
   }

   public LineCommand add(GridBagCommand command)
   {
      return commandListSupport.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         int lineIndex = calculateThisLineIndex(builder);
         applyLineSpecToGrid(builder, lineIndex);

         for (GridBagCommand command : commandListSupport)
         {
            validateWhetherStillInThisLine(builder, lineIndex);
            command.apply(builder);
         }
      }
      catch (GridBagException e)
      {
         logger.log(Level.WARNING, e.getMessage(), e);
      }
      builder.setEndOfLine(true);
   }

   private int calculateThisLineIndex(GridBagBuilder builder) throws GridBagException
   {
      if (builder.isEmpty())
      {
         return 0;
      }
      else
      {
         builder.moveToFreeCell();

         builder.setEndOfLine(true);
         builder.moveToPreviousCell();

         builder.setEndOfLine(true);
         builder.moveToNextFreeCell();

         return builder.getCurrentLineIndex();
      }
   }

   private void applyLineSpecToGrid(GridBagBuilder builder, int lineIndex)
   {
      if (builder.isHorizontal())
      {
         builder.getGridSpec().withRow(lineIndex, lineSpecSupport.getLineSpec());
      }
      else
      {
         builder.getGridSpec().withColumn(lineIndex, lineSpecSupport.getLineSpec());
      }
   }

   private void validateWhetherStillInThisLine(GridBagBuilder builder, int lineIndex) throws GridBagException
   {
      builder.moveToFreeCell();
      int currentLineIndex = builder.getCurrentLineIndex();
      if (lineIndex != currentLineIndex)
      {
         builder.moveToPreviousCell();
         throw new GridBagException("Components from line " + lineIndex + " cannot be placed at line " + currentLineIndex + ".");
      }
   }

   public LineCommand withDefault(CellSpec spec)
   {
      return lineSpecSupport.withDefault(spec);
   }

   public LineCommand withCell(int i, CellSpec spec)
   {
      return lineSpecSupport.withCell(i, spec);
   }

   public LineCommand withLine(LineSpec lineSpec)
   {
      return lineSpecSupport.withLine(lineSpec);
   }
}
