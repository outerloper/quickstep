package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.spec;

public class GridBagCommandsCollectorComponent<T extends GridBagCommandsCollector<T>> implements GridBagCommandsCollector<T>, Iterable<GridBagCommand>
{
   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();
   private T owner;

   public GridBagCommandsCollectorComponent(T owner)
   {
      this.owner = owner;
   }

   @Override
   public final T nextLine()
   {
      return add(new NextLineCommand());
   }

   @Override
   public final T addBlank()
   {
      return addBlank(spec());
   }

   @Override
   public T addBlank(GridBagSpec spec)
   {
      return add((String) null, spec);
   }

   @Override
   public final T add(String text)
   {
      return add(text, spec());
   }

   @Override
   public T add(String text, GridBagSpec spec)
   {
      return add(new LabelCommand(text, spec));
   }

   @Override
   public final T add(JComponent component)
   {
      return add(component, spec());
   }

   @Override
   public T add(JComponent component, GridBagSpec spec)
   {
      return add(new ComponentCommand(component, spec));
   }

   @Override
   public final T addAll(Iterable<? extends JComponent> components)
   {
      return addAll(components, spec());
   }

   @Override
   public final T addAll(Iterable<? extends JComponent> components, GridBagSpec spec)
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
   public T addVerticalSeparator()
   {
      return add(new SeparatorCommand(true));
   }

   @Override
   public T addHorizontalSeparator()
   {
      return add(new SeparatorCommand(false));
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
