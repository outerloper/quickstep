package org.quickstep;

import javax.swing.*;

public class SeqCommand implements GridBagCommand, GridBagCommandsCollector<SeqCommand>
{
   GridBagCommandsCollectorComponent<SeqCommand> commandsCollector = new GridBagCommandsCollectorComponent<SeqCommand>(this);

   protected SeqCommand()
   {
   }

   @Override
   public SeqCommand nextLine()
   {
      return commandsCollector.nextLine();
   }

   @Override
   public SeqCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   @Override
   public SeqCommand add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   @Override
   public SeqCommand add(JComponent component, GridBagSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public SeqCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public SeqCommand add(String text, GridBagSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public SeqCommand addAll(Iterable<? extends JComponent> components, GridBagSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public SeqCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   @Override
   public SeqCommand addBlank(GridBagSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public SeqCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public SeqCommand addSeparatingLine()
   {
      return commandsCollector.addSeparatingLine();
   }

   @Override
   public SeqCommand addSeparator()
   {
      return commandsCollector.addSeparator();
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
