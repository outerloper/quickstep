package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.logger;

public class LineCommand implements GridBagCommand, GridBagCommandsCollector<LineCommand>, LineSpecBuilder<LineCommand>
{
   private final LineSpec lineSpec = new LineSpec();
   private final GridBagCommandsCollectorComponent<LineCommand> commandsCollector = new GridBagCommandsCollectorComponent<LineCommand>(this);

   protected LineCommand()
   {
   }

   @Override
   public LineCommand addLineBreak()
   {
      return commandsCollector.addLineBreak();
   }

   @Override
   public LineCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   @Override
   public LineCommand add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   @Override
   public LineCommand add(JComponent component, CellSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public LineCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public LineCommand add(String text, CellSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public LineCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public LineCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public LineCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   @Override
   public LineCommand addBlank(CellSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public LineCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public LineCommand addLineSeparator()
   {
      return commandsCollector.addLineSeparator();
   }

   @Override
   public LineCommand addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         int lineIndex = calculateThisLineIndex(builder);
         applyLineSpecToGrid(builder, lineIndex);

         for (GridBagCommand command : commandsCollector)
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
         builder.getGridSpec().specifyRow(lineIndex, lineSpec); // TODO refactor not to expose gridSpec
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
