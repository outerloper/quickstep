package org.quickstep.support;

/*
 * ---------------------------------------------------------------------
 * Quickstep
 * ------
 * Copyright (C) 2014 Konrad Sacala
 * ------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * ---------------------------------------------------------------------
 */


import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import org.quickstep.GridBagToolKit;
import org.quickstep.spec.CellSpec;

public class SizeGroupsSupport
{
   Map<Integer, List<JComponent>> groupsX = new TreeMap<Integer, List<JComponent>>();
   Map<Integer, List<JComponent>> groupsY = new TreeMap<Integer, List<JComponent>>();

   public SizeGroupsSupport()
   {
   }

   public static JComponent applyPreferredSize(JComponent component, CellSpec calculatedSpec)
   {
      Integer width = calculatedSpec.getPreferredWidth();
      if (width != null)
      {
         component.setPreferredSize(new Dimension(width, component.getPreferredSize().height));
      }
      Integer height = calculatedSpec.getPreferredHeight();
      if (height != null)
      {
         component.setPreferredSize(new Dimension(component.getPreferredSize().width, height));
      }
      if (width != null || height != null)
      {
         return GridBagToolKit.ResizablePanel.wrap(component);
      }
      return component;
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
