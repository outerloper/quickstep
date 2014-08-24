package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import org.quickstep.support.CommandListSupport;
import org.quickstep.support.LineSpecSupport;

import static org.quickstep.GridBagToolKit.*;

public class LineCommand implements GridBagCommand
{
   private final LineSpecSupport<LineCommand> lineSpecSupport = new LineSpecSupport<LineCommand>(this);
   private final CommandListSupport<LineCommand> commandListSupport = new CommandListSupport<LineCommand>(this);

   protected LineCommand()
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

   public LineCommand add(String text)
   {
      return commandListSupport.add(text);
   }

   public LineCommand add(String text, CellSpec spec)
   {
      return commandListSupport.add(text, spec);
   }

   public LineCommand add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public LineCommand add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public LineCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandListSupport.addAll(components);
   }

   public LineCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.addAll(components, spec);
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
