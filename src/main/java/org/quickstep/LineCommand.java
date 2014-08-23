package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineCommand implements GridBagCommand, LineSpecBuilder<LineCommand>
{
   private final LineSpec lineSpec = new LineSpec();
   private final CommandsBuilder<LineCommand> commandsBuilder = new CommandsBuilder<LineCommand>(this);

   protected LineCommand()
   {
   }

   public LineCommand addBlank()
   {
      return commandsBuilder.addBlank();
   }

   public LineCommand addBlank(CellSpec spec)
   {
      return commandsBuilder.addBlank(spec);
   }

   public LineCommand add(String text)
   {
      return commandsBuilder.add(text);
   }

   public LineCommand add(String text, CellSpec spec)
   {
      return commandsBuilder.add(text, spec);
   }

   public LineCommand add(JComponent component)
   {
      return commandsBuilder.add(component);
   }

   public LineCommand add(JComponent component, CellSpec spec)
   {
      return commandsBuilder.add(component, spec);
   }

   public LineCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsBuilder.addAll(components);
   }

   public LineCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsBuilder.addAll(components, spec);
   }

   public LineCommand addSeparator()
   {
      return commandsBuilder.addSeparator();
   }

   public LineCommand add(GridBagCommand command)
   {
      return commandsBuilder.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         int lineIndex = calculateThisLineIndex(builder);
         applyLineSpecToGrid(builder, lineIndex);

         for (GridBagCommand command : commandsBuilder)
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
         builder.getGridSpec().specifyRow(lineIndex, lineSpec);
      }
      else
      {
         builder.getGridSpec().specifyColumn(lineIndex, lineSpec);
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

   @Override
   public LineCommand specifyDefault(CellSpec spec)
   {
      lineSpec.specifyDefault(spec);
      return this;
   }

   @Override
   public LineCommand specifyCell(int i, CellSpec spec)
   {
      lineSpec.specifyCell(i, spec);
      return this;
   }
}
