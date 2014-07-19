package org.quickstep;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.spec;

public class GridBagLine implements Iterable<GridBagLine.GridBagLineEntry>
{
   private final List<GridBagLineEntry> elements = new LinkedList<GridBagLineEntry>();

   public GridBagLine add(JComponent component)
   {
      elements.add(new GridBagLineEntry(component, spec()));
      return this;
   }

   public GridBagLine add(JComponent component, GridBagSpec spec)
   {
      elements.add(new GridBagLineEntry(component, spec));
      return this;
   }

   static class GridBagLineEntry
   {
      private JComponent component;
      private GridBagSpec spec;

      GridBagLineEntry(JComponent component, GridBagSpec spec)
      {
         this.component = component;
         this.spec = spec;
      }

      JComponent getComponent()
      {
         return component;
      }

      GridBagSpec getSpec()
      {
         return spec;
      }
   }

   @Override
   public Iterator<GridBagLineEntry> iterator()
   {
      return elements.iterator();
   }
}
