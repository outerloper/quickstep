package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeqCommand implements GridBagCommand, Iterable<GridBagCommand>
{
   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();

   public SeqCommand()
   {
   }

   public final SeqCommand addLineBreak()
   {
      return add(new LineBreakCommand());
   }

   public final SeqCommand addBlank()
   {
      return addBlank(spec());
   }

   public SeqCommand addBlank(CellSpec spec)
   {
      return add((String) null, spec);
   }

   public final SeqCommand add(String text)
   {
      return add(text, spec());
   }

   public SeqCommand add(String text, CellSpec spec)
   {
      return add(new LabelCommand(text).withSpec(spec));
   }

   public final SeqCommand add(JComponent component)
   {
      return add(component, spec());
   }

   public SeqCommand add(JComponent component, CellSpec spec)
   {
      return add(component(component).withSpec(spec));
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components)
   {
      return addAll(components, spec());
   }

   public final SeqCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      for (JComponent component : components)
      {
         add(component, spec);
      }
      return this;
   }

   public SeqCommand addHeader(String title)
   {
      return add(new HeaderCommand(title));
   }

   public SeqCommand addSeparator()
   {
      return add(new SeparatorCommand());
   }

   public SeqCommand addLineSeparator()
   {
      return add(new LineSeparatorCommand());
   }

   public SeqCommand add(GridBagCommand command)
   {
      if (command == null)
      {
         throw new NullPointerException();
      }
      commands.add(command);
      return this;
   }

   public Iterator<GridBagCommand> iterator()
   {
      return commands.iterator();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      for (GridBagCommand command : this)
      {
         command.apply(builder);
      }
   }
}
