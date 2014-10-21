package org.quickstep.command;

import javax.swing.*;

import org.quickstep.spec.CellSpec;
import org.quickstep.support.CommandListSupport;

public class SeqCommand implements GridBagCommand
{
   private final CommandListSupport<SeqCommand> commandListSupport = new CommandListSupport<SeqCommand>(this);

   public SeqCommand()
   {
   }

   public SeqCommand addLineBreak()
   {
      return commandListSupport.addLineBreak();
   }

   public SeqCommand addBlank()
   {
      return commandListSupport.addBlank();
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public SeqCommand add(String label)
   {
      return commandListSupport.add(label);
   }

   public SeqCommand add(String label, CellSpec spec)
   {
      return commandListSupport.add(label, spec);
   }

   public SeqCommand add(String label, JComponent component)
   {
      return commandListSupport.add(label, component);
   }

   public SeqCommand add(String label, JComponent component, CellSpec spec)
   {
      return commandListSupport.add(label, component, spec);
   }

   public SeqCommand add(String label, GridBagCommand command)
   {
      return commandListSupport.add(label, command);
   }

   public SeqCommand add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public SeqCommand add(Iterable<? extends JComponent> components)
   {
      return commandListSupport.add(components);
   }

   public SeqCommand add(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.add(components, spec);
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
