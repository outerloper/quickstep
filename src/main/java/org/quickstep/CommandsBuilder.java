package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class CommandsBuilder<T> implements Iterable<GridBagCommand>
{
   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();
   private T owner;

   public CommandsBuilder(T owner)
   {
      this.owner = owner;
   }

   public final T addLineBreak()
   {
      return add(new LineBreakCommand());
   }

   public final T addBlank()
   {
      return addBlank(spec());
   }

   public T addBlank(CellSpec spec)
   {
      return add((String) null, spec);
   }

   public final T add(String text)
   {
      return add(text, spec());
   }

   public T add(String text, CellSpec spec)
   {
      return add(new LabelCommand(text).withSpec(spec));
   }

   public final T add(JComponent component)
   {
      return add(component, spec());
   }

   public T add(JComponent component, CellSpec spec)
   {
      return add(component(component).withSpec(spec));
   }

   public final T addAll(Iterable<? extends JComponent> components)
   {
      return addAll(components, spec());
   }

   public final T addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      for (JComponent component : components)
      {
         add(component, spec);
      }
      return owner;
   }

   public T addHeader(String title)
   {
      return add(new HeaderCommand(title));
   }

   public T addSeparator()
   {
      return add(new SeparatorCommand());
   }

   public T addLineSeparator()
   {
      return add(new LineSeparatorCommand());
   }

   public T add(GridBagCommand command)
   {
      if (command == null)
      {
         throw new NullPointerException();
      }
      commands.add(command);
      return owner;
   }

   @Override
   public Iterator<GridBagCommand> iterator()
   {
      return commands.iterator();
   }
}
