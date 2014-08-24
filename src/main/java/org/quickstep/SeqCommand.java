package org.quickstep;

import javax.swing.*;

import org.quickstep.support.CommandListSupport;

public class SeqCommand implements GridBagCommand
{
   private final CommandListSupport<SeqCommand> commandListSupport = new CommandListSupport<SeqCommand>(this);

   protected SeqCommand()
   {
   }

   public final SeqCommand addLineBreak()
   {
      return commandListSupport.addLineBreak();
   }

   public final SeqCommand addBlank()
   {
      return commandListSupport.addBlank();
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public final SeqCommand add(String text)
   {
      return commandListSupport.add(text);
   }

   public SeqCommand add(String text, CellSpec spec)
   {
      return commandListSupport.add(text, spec);
   }

   public final SeqCommand add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandListSupport.addAll(components);
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.addAll(components, spec);
   }

   public SeqCommand addHeader(String title)
   {
      return commandListSupport.addHeader(title);
   }

   public SeqCommand addSeparator()
   {
      return commandListSupport.addSeparator();
   }

   public SeqCommand addLineSeparator()
   {
      return commandListSupport.addLineSeparator();
   }

   public SeqCommand add(GridBagCommand command)
   {
      return commandListSupport.add(command);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      for (GridBagCommand command : commandListSupport)
      {
         command.apply(builder);
      }
   }
}
