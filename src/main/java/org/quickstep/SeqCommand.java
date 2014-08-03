package org.quickstep;

import javax.swing.*;

public class SeqCommand implements GridBagCommand, GridBagCommandsCollector<SeqCommand>
{
   private final GridBagCommandsCollectorComponent<SeqCommand> commandsCollector = new GridBagCommandsCollectorComponent<SeqCommand>(this);

   protected SeqCommand()
   {
   }

   @Override
   public SeqCommand addLineBreak()
   {
      return commandsCollector.addLineBreak();
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
   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public SeqCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public SeqCommand add(String text, CellSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public SeqCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public SeqCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   @Override
   public SeqCommand addBlank(CellSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public SeqCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public SeqCommand addHorizontalSeparator()
   {
      return commandsCollector.addHorizontalSeparator();
   }

   @Override
   public SeqCommand addVerticalSeparator()
   {
      return commandsCollector.addVerticalSeparator();
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
