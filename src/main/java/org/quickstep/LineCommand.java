package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.logger;

public class LineCommand implements GridBagCommand, GridBagCommandsCollector<LineCommand>
{
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
   public LineCommand add(JComponent component, GridBagSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public LineCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public LineCommand add(String text, GridBagSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public LineCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public LineCommand addAll(Iterable<? extends JComponent> components, GridBagSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public LineCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   @Override
   public LineCommand addBlank(GridBagSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public LineCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public LineCommand addSeparatingLine()
   {
      return commandsCollector.addSeparatingLine();
   }

   @Override
   public LineCommand addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.setEndOfLine(!builder.isEmpty());
      builder.moveToNextFreeCell();
      int lineNumber = builder.getCurrentLineNumber();
      builder.moveToPreviousCell();

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
}
