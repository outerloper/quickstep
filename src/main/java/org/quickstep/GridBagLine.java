package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.spec;

public class GridBagLine implements Iterable<GridBagCommand>
{
   private final List<GridBagCommand> elements = new LinkedList<GridBagCommand>();

   public GridBagLine add()
   {
      return add(spec());
   }

   public GridBagLine add(GridBagSpec spec)
   {
      return add((String) null, spec);
   }

   public GridBagLine add(String text)
   {
      return add(text, spec());
   }

   public GridBagLine add(String text, GridBagSpec spec)
   {
      return add(new JLabel(text), spec);
   }

   public GridBagLine add(JComponent component)
   {
      return add(component, spec());
   }

   public GridBagLine add(JComponent component, GridBagSpec spec)
   {
      elements.add(new AddComponentCommand(new ComponentBuilder.ComponentBuilderAdapter(component), spec));
      return this;
   }

   @Override
   public Iterator<GridBagCommand> iterator()
   {
      return elements.iterator();
   }
}
