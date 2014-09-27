package org.quickstep.support;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import org.quickstep.CellSpec;

public class SizeGroupsSupport
{
   Map<Integer, List<JComponent>> groupsX = new TreeMap<Integer, List<JComponent>>();
   Map<Integer, List<JComponent>> groupsY = new TreeMap<Integer, List<JComponent>>();

   public SizeGroupsSupport()
   {
   }

   public void add(JComponent component, CellSpec spec)
   {
      Integer groupX = spec.getSizeGroupX();
      if (CellSpec.isSizeGroup(groupX))
      {
         if (!groupsX.containsKey(groupX))
         {
            groupsX.put(groupX, new LinkedList<JComponent>());
         }
         List<JComponent> componentList = groupsX.get(groupX);
         componentList.add(component);
      }
      Integer groupY = spec.getSizeGroupY();
      if (CellSpec.isSizeGroup(groupY))
      {
         if (!groupsY.containsKey(groupY))
         {
            groupsY.put(groupY, new LinkedList<JComponent>());
         }
         List<JComponent> componentList = groupsY.get(groupY);
         componentList.add(component);
      }
   }

   public void process()
   {
      processGroupsX();
      processGroupsY();
   }

   private void processGroupsX()
   {
      for (Map.Entry<Integer, List<JComponent>> entry : groupsX.entrySet())
      {
         int maxWidth = 0;
         List<JComponent> componentList = entry.getValue();
         for (JComponent component : componentList)
         {
            int width = component.getPreferredSize().width;
            if (width > maxWidth)
            {
               maxWidth = width;
            }
         }
         for (JComponent component : componentList)
         {
            component.setPreferredSize(new Dimension(maxWidth, component.getPreferredSize().height));
         }
      }
   }

   private void processGroupsY()
   {
      for (Map.Entry<Integer, List<JComponent>> entry : groupsY.entrySet())
      {
         int maxHeight = 0;
         List<JComponent> componentList = entry.getValue();
         for (JComponent component : componentList)
         {
            int height = component.getPreferredSize().height;
            if (height > maxHeight)
            {
               maxHeight = height;
            }
         }
         for (JComponent component : componentList)
         {
            component.setPreferredSize(new Dimension(component.getPreferredSize().width, maxHeight));
         }
      }
   }
}
