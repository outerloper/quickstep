package org.quickstep.support;

import java.awt.*;
import javax.swing.*;

import org.quickstep.*;

import static org.quickstep.GridBagToolKit.spec;

public class ContentAnchorSupport<T>
{
   private final T owner;

   private GridBagToolKit.AX anchorX;
   private GridBagToolKit.AY anchorY;

   public ContentAnchorSupport(T owner)
   {
      this.owner = owner;
   }

   public T withContentAnchor(GridBagToolKit.AX anchorX, GridBagToolKit.AY anchorY)
   {
      withContentAnchorX(anchorX);
      withContentAnchorY(anchorY);
      return owner;
   }

   public T withContentAnchor(GridBagToolKit.A anchorXAndY)
   {
      if (GridBagToolKit.A.CENTER.equals(anchorXAndY))
      {
         return withContentAnchor(GridBagToolKit.AX.CENTER, GridBagToolKit.AY.CENTER);
      }
      return withContentAnchor(GridBagToolKit.AX.BOTH, GridBagToolKit.AY.BOTH);
   }

   public T withContentAnchorX(GridBagToolKit.AX anchorX)
   {
      this.anchorX = anchorX;
      return owner;
   }

   public T withContentAnchorY(GridBagToolKit.AY anchorY)
   {
      this.anchorY = anchorY;
      return owner;
   }

   // TODO extract GridSpecBuilderComponent, let ComponentCommand not override GridSpecBuilder
   // TODO think about doing the same with LineSpecBuilder
   public JComponent applyContentAnchor(JComponent container, GridSpec gridSpec)
   {
      JComponent result = container;

      Integer contentX = null;
      if (anchorX == null)
      {
         anchorX = GridBagToolKit.AX.CENTER;
      }
      switch (anchorX)
      {
         case LEFT:
            contentX = 0;
            break;
         case RIGHT:
            contentX = 1;
            break;
         case BOTH:
            gridSpec.specifyDefault(spec().withWeightX(1.0).withAnchorX(GridBagToolKit.AX.BOTH));
            break;
      }
      Integer contentY = null;
      if (anchorY == null)
      {
         anchorY = GridBagToolKit.AY.CENTER;
      }
      switch (anchorY)
      {
         case TOP:
            contentY = 0;
            break;
         case BOTTOM:
            contentY = 1;
            break;
         case BOTH:
            gridSpec.specifyDefault(spec().withWeightY(1.0).withAnchorY(GridBagToolKit.AY.BOTH));
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
         if (anchorX == GridBagToolKit.AX.BOTH)
         {
            contentSpec.withWeightX(1.0).withAnchorX(GridBagToolKit.AX.BOTH);
         }
         if (anchorY == GridBagToolKit.AY.BOTH)
         {
            contentSpec.withWeightY(1.0).withAnchorY(GridBagToolKit.AY.BOTH);
         }
         result.add(new JLabel(), fillSpec.toConstraints(fillX, fillY));
         result.add(container, contentSpec.toConstraints(contentX, contentY));
      }
      return result;
   }
}
