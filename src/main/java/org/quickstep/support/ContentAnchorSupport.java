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
import javax.swing.*;

import org.quickstep.spec.CellSpec;
import org.quickstep.spec.GridSpec;

import static org.quickstep.GridBagToolKit.*;

public class ContentAnchorSupport<T>
{
   private final T owner;

   private AX contentAnchorX;
   private AY contentAnchorY;

   public static ContentAnchorSupport<ContentAnchorSupport> createDefault()
   {
      return new ContentAnchorSupport<ContentAnchorSupport>(null);
   }

   public ContentAnchorSupport(T owner)
   {
      try
      {
         //noinspection unchecked
         this.owner = owner != null ? owner : (T) this;
      }
      catch (ClassCastException e)
      {
         throw new RuntimeException("null owner is only allowed for ContentAnchorSupport<? extends ContentAnchorSupport>.");
      }
   }

   public T withContentAnchor(AX anchorX, AY anchorY)
   {
      withContentAnchorX(anchorX);
      withContentAnchorY(anchorY);
      return owner;
   }

   public T withContentAnchor(A anchorXAndY)
   {
      if (A.CENTER.equals(anchorXAndY))
      {
         return withContentAnchor(AX.CENTER, AY.CENTER);
      }
      return withContentAnchor(AX.BOTH, AY.BOTH);
   }

   public T withContentAnchorX(AX anchorX)
   {
      if (anchorX != null)
      {
         this.contentAnchorX = anchorX;
      }
      return owner;
   }

   public T withContentAnchorY(AY anchorY)
   {
      if (anchorY != null)
      {
         this.contentAnchorY = anchorY;
      }
      return owner;
   }

   public AX getContentAnchorX()
   {
      return contentAnchorX;
   }

   public AY getContentAnchorY()
   {
      return contentAnchorY;
   }

   public JComponent applyContentAnchor(JComponent container, GridSpec gridSpec)
   {
      JComponent result = container;

      Integer contentX = null;
      if (contentAnchorX == null)
      {
         contentAnchorX = AX.CENTER;
      }
      switch (contentAnchorX)
      {
         case LEFT:
            contentX = 0;
            break;
         case RIGHT:
            contentX = 1;
            break;
         case BOTH:
            gridSpec.withDefault(spec().withWeightX(1.0).withAnchorX(AX.BOTH));
            break;
      }
      Integer contentY = null;
      if (contentAnchorY == null)
      {
         contentAnchorY = AY.CENTER;
      }
      switch (contentAnchorY)
      {
         case TOP:
            contentY = 0;
            break;
         case BOTTOM:
            contentY = 1;
            break;
         case BOTH:
            gridSpec.withDefault(spec().withWeightY(1.0).withAnchorY(AY.BOTH));
            break;
      }
      if (contentX != null || contentY != null)
      {
         int fillX = 0;
         CellSpec fillSpec = spec();
         if (contentX != null)
         {
            fillSpec.withWeightX(1.0);
            fillX = 1 - contentX;
         }
         else
         {
            contentX = 0;
         }
         int fillY = 0;
         if (contentY != null)
         {
            fillSpec.withWeightY(1.0);
            fillY = 1 - contentY;
         }
         else
         {
            contentY = 0;
         }
         result = new JPanel(new GridBagLayout());
         CellSpec contentSpec = spec();
         if (contentAnchorX == AX.BOTH)
         {
            contentSpec.withWeightX(1.0).withAnchorX(AX.BOTH);
         }
         if (contentAnchorY == AY.BOTH)
         {
            contentSpec.withWeightY(1.0).withAnchorY(AY.BOTH);
         }
         result.add(new JLabel(), fillSpec.toConstraints(fillX, fillY));
         result.add(container, contentSpec.toConstraints(contentX, contentY));
      }
      return result;
   }
}
