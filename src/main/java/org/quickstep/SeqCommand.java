package org.quickstep;

import javax.swing.*;

import org.quickstep.support.CommandsBuilder;

public class SeqCommand implements GridBagCommand
{
   private final CommandsBuilder<SeqCommand> commandsBuilder = new CommandsBuilder<SeqCommand>(this);

   public SeqCommand()
   {
   }

   public final SeqCommand addLineBreak()
   {
      return commandsBuilder.addLineBreak();
   }

   public final SeqCommand addBlank()
   {
      return commandsBuilder.addBlank();
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return commandsBuilder.addBlank(spec);
   }

   public final SeqCommand add(String text)
   {
      return commandsBuilder.add(text);
   }

   public SeqCommand add(String text, CellSpec spec)
   {
      return commandsBuilder.add(text, spec);
   }

   public final SeqCommand add(JComponent component)
   {
      return commandsBuilder.add(component);
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandsBuilder.add(component, spec);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsBuilder.addAll(components);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsBuilder.addAll(components, spec);
   }

   public SeqCommand addHeader(String title)
   {
      return commandsBuilder.addHeader(title);
   }

   public SeqCommand addSeparator()
   {
      return commandsBuilder.addSeparator();
   }

   public SeqCommand addLineSeparator()
   {
      return commandsBuilder.addLineSeparator();
   }

   public SeqCommand add(GridBagCommand command)
   {
      return commandsBuilder.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      for (GridBagCommand command : commandsBuilder)
      {
         command.apply(builder);
      }
   }
}
