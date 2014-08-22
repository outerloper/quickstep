package org.quickstep;

import javax.swing.*;

public class SeqCommand implements GridBagCommand
{
   private final CommandsCollector<SeqCommand> commandsCollector = new CommandsCollector<SeqCommand>(this);

   public SeqCommand()
   {
   }

   public final SeqCommand addLineBreak()
   {
      return commandsCollector.addLineBreak();
   }

   public final SeqCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   public final SeqCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   public SeqCommand add(String text, CellSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   public final SeqCommand add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   public SeqCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   public SeqCommand addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   public SeqCommand addLineSeparator()
   {
      return commandsCollector.addLineSeparator();
   }

   public SeqCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      for (GridBagCommand command : commandsCollector)
      {
         command.apply(builder);
      }
   }
}
