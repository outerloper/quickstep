package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineCommand implements GridBagCommand, LineSpecBuilder<LineCommand>
{
   private final LineSpec lineSpec = new LineSpec();
   private final SeqCommand commands = seq();

   protected LineCommand()
   {
   }

   public LineCommand add(GridBagCommand command)
   {
      commands.add(command);
      return this;
   }

   public LineCommand add(JComponent component)
   {
      commands.add(component);
      return this;
   }

   public LineCommand add(JComponent component, CellSpec spec)
   {
      commands.add(component, spec);
      return this;
   }

   public LineCommand add(String text)
   {
      commands.add(text);
      return this;
   }

   public LineCommand add(String text, CellSpec spec)
   {
      commands.add(text, spec);
      return this;
   }

   public LineCommand addAll(Iterable<? extends JComponent> components)
   {
      commands.addAll(components);
      return this;
   }

   public LineCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      commands.addAll(components, spec);
      return this;
   }

   public LineCommand addBlank()
   {
      commands.addBlank();
      return this;
   }

   public LineCommand addBlank(CellSpec spec)
   {
      commands.addBlank(spec);
      return this;
   }

   public LineCommand addSeparator()
   {
      commands.addSeparator();
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         int lineIndex = calculateThisLineIndex(builder);
         applyLineSpecToGrid(builder, lineIndex);

         for (GridBagCommand command : commands)
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
