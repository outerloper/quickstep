package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class GridBagCommandsCollectorComponent<T extends GridBagCommandsCollector<T>> implements GridBagCommandsCollector<T>, Iterable<GridBagCommand>
{
   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();
   private T owner;

   public GridBagCommandsCollectorComponent(T owner)
   {
      this.owner = owner;
   }

   @Override
   public final T addLineBreak()
   {
      return add(new LineBreakCommand());
   }

   @Override
   public final T addBlank()
   {
      return addBlank(spec());
   }

   @Override
   public T addBlank(CellSpec spec)
   {
      return add((String) null, spec);
   }

   @Override
   public final T add(String text)
   {
      return add(text, spec());
   }

   @Override
   public T add(String text, CellSpec spec)
   {
      return add(new LabelCommand(text).withSpec(spec));
   }

   @Override
   public final T add(JComponent component)
   {
      return add(component, spec());
   }

   @Override
   public T add(JComponent component, CellSpec spec)
   {
      return add(component(component).withSpec(spec));
   }

   @Override
   public final T addAll(Iterable<? extends JComponent> components)
   {
      return addAll(components, spec());
   }

   @Override
   public final T addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      for (JComponent component : components)
      {
         add(component, spec);
      }
      return owner;
   }

   @Override
   public T addHeader(String title)
   {
      return add(new HeaderCommand(title));
   }

   @Override
   public T addSeparator()
   {
      return add(new SeparatorCommand());
   }

   @Override
   public T addLineSeparator()
   {
      return add(new LineSeparatorCommand());
   }

   @Override
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
