package org.quickstep;

import java.util.Map;
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
   public LineCommand nextLine()
   {
      return commandsCollector.nextLine();
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
   public LineCommand addHorizontalSeparator()
   {
      return commandsCollector.addHorizontalSeparator();
   }

   @Override
   public LineCommand addVerticalSeparator()
   {
      return commandsCollector.addVerticalSeparator();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.setEndOfLine(!builder.isEmpty());
      builder.moveToNextFreeCell();
      int lineNumber = builder.getCurrentLineNumber();
      builder.moveToPreviousCell();

      GridSpec gridSpec = builder.getGridSpec();
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells())
      {
         if (builder.isHorizontal())
         {
            gridSpec.specifyCell(entry.getKey(), lineNumber, entry.getValue()); // TODO test this
         }
         else
         {
            gridSpec.specifyCell(lineNumber, entry.getKey(), entry.getValue());
         }
      }

      for (GridBagCommand command : commandsCollector)
      {
         builder.moveToFreeCell();
         int currentLineNumber = builder.getCurrentLineNumber();
         if (lineNumber != currentLineNumber)
         {
            logger.log(Level.WARNING, "Components from GridBagLine #" + lineNumber + " cannot be placed at line " + currentLineNumber + ".");
            builder.moveToPreviousCell();
            break;
         }
         command.apply(builder);
      }
      builder.setEndOfLine(true);
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
