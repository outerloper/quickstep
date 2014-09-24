package org.quickstep;

import java.awt.*;

import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.support.DebugSupport.*;

public final class CellSpec
{
   private static final String WILDCARD = "*";

   private Integer preferredWidth;
   private Integer preferredHeight;
   private Integer gridWidth;
   private Integer gridHeight;
   private Double weightX;
   private Double weightY;
   private AX anchorX;
   private AY anchorY;
   private Boolean baseline;
   private Integer insetTop;
   private Integer insetLeft;
   private Integer insetBottom;
   private Integer insetRight;
   private Integer iPadX;
   private Integer iPadY;

   CellSpec()
   {
   }

   CellSpec(CellSpec that)
   {
      this(that.preferredWidth,
           that.preferredHeight,
           that.gridWidth,
           that.gridHeight,
           that.weightX,
           that.weightY,
           that.anchorX,
           that.anchorY,
           that.baseline,
           that.insetTop,
           that.insetLeft,
           that.insetBottom,
           that.insetRight,
           that.iPadX,
           that.iPadY
      );
   }

   public CellSpec(Integer preferredWidth,
                   Integer preferredHeight,
                   Integer gridWidth,
                   Integer gridHeight,
                   Double weightX,
                   Double weightY,
                   AX anchorX,
                   AY anchorY,
                   Boolean baseline,
                   Integer insetTop,
                   Integer insetLeft,
                   Integer insetBottom,
                   Integer insetRight,
                   Integer iPadX,
                   Integer iPadY)
   {
      this.preferredWidth = preferredWidth;
      this.preferredHeight = preferredHeight;
      this.gridWidth = gridWidth;
      this.gridHeight = gridHeight;
      this.weightX = weightX;
      this.weightY = weightY;
      this.anchorX = anchorX;
      this.anchorY = anchorY;
      this.baseline = baseline;
      this.insetTop = insetTop;
      this.insetLeft = insetLeft;
      this.insetBottom = insetBottom;
      this.insetRight = insetRight;
      this.iPadX = iPadX;
      this.iPadY = iPadY;
   }

   public CellSpec overwriteWith(CellSpec that)
   {
      preferredWidth = that.preferredWidth;
      preferredHeight = that.preferredHeight;
      gridWidth = that.gridWidth;
      gridHeight = that.gridHeight;
      weightX = that.weightX;
      weightY = that.weightY;
      anchorX = that.anchorX;
      anchorY = that.anchorY;
      baseline = that.baseline;
      insetTop = that.insetTop;
      insetLeft = that.insetLeft;
      insetBottom = that.insetBottom;
      insetRight = that.insetRight;
      iPadX = that.iPadX;
      iPadY = that.iPadY;
      return this;
   }

   public CellSpec derive()
   {
      return new CellSpec(this);
   }

   @SuppressWarnings("ConstantConditions")
   public CellSpec overrideWith(CellSpec that)
   {
      if (that == null)
      {
         return this;
      }
      if (that.preferredWidth != null)
      {
         preferredWidth = that.preferredWidth;
      }
      if (that.preferredHeight != null)
      {
         preferredHeight = that.preferredHeight;
      }
      if (that.gridWidth != null)
      {
         gridWidth = that.gridWidth;
      }
      if (that.gridHeight != null)
      {
         gridHeight = that.gridHeight;
      }
      if (that.weightX != null)
      {
         weightX = that.weightX;
      }
      if (that.weightY != null)
      {
         weightY = that.weightY;
      }
      if (that.anchorX != null)
      {
         anchorX = that.anchorX;
      }
      if (that.anchorY != null)
      {
         anchorY = that.anchorY;
      }
      if (that.baseline != null)
      {
         baseline = that.baseline;
      }
      if (that.insetTop != null)
      {
         insetTop = that.insetTop;
      }
      if (that.insetBottom != null)
      {
         insetBottom = that.insetBottom;
      }
      if (that.insetLeft != null)
      {
         insetLeft = that.insetLeft;
      }
      if (that.insetRight != null)
      {
         insetRight = that.insetRight;
      }
      if (that.iPadX != null)
      {
         iPadX = that.iPadX;
      }
      if (that.iPadY != null)
      {
         iPadY = that.iPadY;
      }
      return this;
   }


   public Integer getPreferredWidth()
   {
      return preferredWidth;
   }

   public Integer getPreferredHeight()
   {
      return preferredHeight;
   }

   public Integer getGridWidth()
   {
      return gridWidth;
   }

   public Integer getGridHeight()
   {
      return gridHeight;
   }

   public Double getWeightX()
   {
      return weightX;
   }

   public Double getWeightY()
   {
      return weightY;
   }

   public AX getAnchorX()
   {
      return anchorX;
   }

   public AY getAnchorY()
   {
      return anchorY;
   }

   public Boolean getBaseline()
   {
      return baseline;
   }

   public Integer getInsetTop()
   {
      return insetTop;
   }

   public Integer getInsetLeft()
   {
      return insetLeft;
   }

   public Integer getInsetBottom()
   {
      return insetBottom;
   }

   public Integer getInsetRight()
   {
      return insetRight;
   }

   public Integer getIPadX()
   {
      return iPadX;
   }

   public Integer getIPadY()
   {
      return iPadY;
   }


   public CellSpec withPreferredWidth(Integer value)
   {
      preferredWidth = value;
      return this;
   }

   public CellSpec withPreferredHeight(Integer value)
   {
      preferredHeight = value;
      return this;
   }

   public CellSpec withPreferredSize(Integer width, Integer height)
   {
      return withPreferredWidth(width).withPreferredHeight(height);
   }

   public CellSpec withGridWidth(Integer value)
   {
      gridWidth = value;
      return this;
   }

   public CellSpec withGridHeight(Integer value)
   {
      gridHeight = value;
      return this;
   }

   public CellSpec withGridWidthRemainder()
   {
      return withGridWidth(GridBagConstraints.REMAINDER);
   }

   public CellSpec withGridHeightRemainder()
   {
      return withGridHeight(GridBagConstraints.REMAINDER);
   }

   public CellSpec withGridSize(Integer width, Integer height)
   {
      return withGridWidth(width).withGridHeight(height);
   }

   public CellSpec withWeightX(Double value)
   {
      weightX = value;
      return this;
   }

   public CellSpec withWeightY(Double value)
   {
      weightY = value;
      return this;
   }

   public CellSpec withWeight(Double x, Double y)
   {
      return withWeightX(x).withWeightY(y);
   }

   public CellSpec withWeight(Double weightXAndY)
   {
      return withWeight(weightXAndY, weightXAndY);
   }

   public CellSpec withAnchorX(AX value)
   {
      this.anchorX = value;
      return this;
   }

   public CellSpec withAnchorY(AY value)
   {
      this.anchorY = value;
      return this;
   }

   public CellSpec withAnchor(AX anchorX, AY anchorY)
   {
      return withAnchorX(anchorX).withAnchorY(anchorY);
   }

   public CellSpec withAnchor(A anchorXAndY)
   {
      if (A.CENTER.equals(anchorXAndY))
      {
         return withAnchor(AX.CENTER, AY.CENTER);
      }
      return withAnchor(AX.BOTH, AY.BOTH);
   }

   public CellSpec withBaseline(Boolean baseline)
   {
      this.baseline = baseline;
      return this;
   }

   public CellSpec withInsetTop(Integer top)
   {
      insetTop = top;
      return this;
   }

   public CellSpec withInsetLeft(Integer left)
   {
      insetLeft = left;
      return this;
   }

   public CellSpec withInsetBottom(Integer bottom)
   {
      insetBottom = bottom;
      return this;
   }

   public CellSpec withInsetRight(Integer right)
   {
      insetRight = right;
      return this;
   }

   public CellSpec withInsetX(Integer leftAndRight)
   {
      return withInsetLeft(leftAndRight).withInsetRight(leftAndRight);
   }

   public CellSpec withInsetY(Integer topAndBottom)
   {
      return withInsetTop(topAndBottom).withInsetBottom(topAndBottom);
   }

   public CellSpec withInset(Integer value)
   {
      return withInset(value, value, value, value);
   }

   public CellSpec withInset(Integer leftAndRight, Integer topAndBottom)
   {
      return withInset(topAndBottom, leftAndRight, topAndBottom, leftAndRight);
   }

   public CellSpec withGap(Integer insetTop, Integer insetLeft)
   {
      return withInsetLeft(insetLeft).withInsetTop(insetTop);
   }

   public CellSpec withGap(Integer insetTopAndLeft)
   {
      return withGap(insetTopAndLeft, insetTopAndLeft);
   }

   public CellSpec withInset(Integer top, Integer left, Integer bottom, Integer right)
   {
      return withInsetTop(top).withInsetLeft(left).withInsetBottom(bottom).withInsetRight(right);
   }

   public CellSpec withIPadX(Integer value)
   {
      iPadX = value;
      return this;
   }

   public CellSpec withIPadY(Integer value)
   {
      iPadY = value;
      return this;
   }

   public CellSpec withIPad(Integer x, Integer y)
   {
      return withIPadX(x).withIPadY(y);
   }

   public CellSpec withIPad(Integer iPadXAndY)
   {
      return withIPad(iPadXAndY, iPadXAndY);
   }


   public GridBagConstraints toConstraints(int x, int y)
   {
      CellSpec spec = completeSpec().overrideWith(this);
      return new GridBagConstraints(x, y,
                                    spec.getGridWidth(), spec.getGridHeight(),
                                    spec.getWeightX(), spec.getWeightY(),
                                    spec.getAnchor(),
                                    spec.getFill(),
                                    new Insets(spec.getInsetTop(),
                                               spec.getInsetLeft(),
                                               spec.getInsetBottom(),
                                               spec.getInsetRight()),
                                    spec.getIPadX(), spec.getIPadY()
      );
   }

   public int getAnchor()
   {
      AX x = anchorX == null ? AX.CENTER : anchorX;
      AY y = anchorY == null ? AY.CENTER : anchorY;
      switch (y)
      {
         case TOP:
            switch (x)
            {
               case LEFT:
                  return GridBagConstraints.FIRST_LINE_START;
               case RIGHT:
                  return GridBagConstraints.FIRST_LINE_END;
            }
            return GridBagConstraints.PAGE_START;
         case BOTTOM:
            switch (x)
            {
               case LEFT:
                  return GridBagConstraints.LAST_LINE_START;
               case RIGHT:
                  return GridBagConstraints.LAST_LINE_END;
            }
            return GridBagConstraints.PAGE_END;
      }
      if (baseline)
      {
         switch (x)
         {
            case LEFT:
               return GridBagConstraints.BASELINE_LEADING;
            case RIGHT:
               return GridBagConstraints.BASELINE_TRAILING;
         }
         return GridBagConstraints.BASELINE;
      }
      switch (x)
      {
         case LEFT:
            return GridBagConstraints.LINE_START;
         case RIGHT:
            return GridBagConstraints.LINE_END;
      }
      return GridBagConstraints.CENTER;
   }

   public int getFill()
   {
      Boolean fillX = anchorX != null && AX.BOTH.equals(anchorX);
      Boolean fillY = anchorY != null && AY.BOTH.equals(anchorY);
      if (fillX)
      {
         if (fillY)
         {
            return GridBagConstraints.BOTH;
         }
         return GridBagConstraints.HORIZONTAL;
      }
      if (fillY)
      {
         return GridBagConstraints.VERTICAL;
      }
      return GridBagConstraints.NONE;
   }

   @Override
   @SuppressWarnings("ConstantConditions")
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (o == null || getClass() != o.getClass())
      {
         return false;
      }

      CellSpec that = (CellSpec) o;

      if (anchorX != that.anchorX)
      {
         return false;
      }
      if (anchorY != that.anchorY)
      {
         return false;
      }
      if (baseline != that.baseline)
      {
         return false;
      }
      if (gridHeight != null ? !gridHeight.equals(that.gridHeight) : that.gridHeight != null)
      {
         return false;
      }
      if (gridWidth != null ? !gridWidth.equals(that.gridWidth) : that.gridWidth != null)
      {
         return false;
      }
      if (iPadX != null ? !iPadX.equals(that.iPadX) : that.iPadX != null)
      {
         return false;
      }
      if (iPadY != null ? !iPadY.equals(that.iPadY) : that.iPadY != null)
      {
         return false;
      }
      if (insetBottom != null ? !insetBottom.equals(that.insetBottom) : that.insetBottom != null)
      {
         return false;
      }
      if (insetLeft != null ? !insetLeft.equals(that.insetLeft) : that.insetLeft != null)
      {
         return false;
      }
      if (insetRight != null ? !insetRight.equals(that.insetRight) : that.insetRight != null)
      {
         return false;
      }
      if (insetTop != null ? !insetTop.equals(that.insetTop) : that.insetTop != null)
      {
         return false;
      }
      if (preferredHeight != null ? !preferredHeight.equals(that.preferredHeight) : that.preferredHeight != null)
      {
         return false;
      }
      if (preferredWidth != null ? !preferredWidth.equals(that.preferredWidth) : that.preferredWidth != null)
      {
         return false;
      }
      if (weightX != null ? !weightX.equals(that.weightX) : that.weightX != null)
      {
         return false;
      }
      if (weightY != null ? !weightY.equals(that.weightY) : that.weightY != null)
      {
         return false;
      }
      //noinspection SimplifiableIfStatement
      return true;
   }

   @Override
   @SuppressWarnings("ConstantConditions")
   public int hashCode()
   {
      int result = preferredWidth != null ? preferredWidth.hashCode() : 0;
      result = 31 * result + (preferredHeight != null ? preferredHeight.hashCode() : 0);
      result = 31 * result + (gridWidth != null ? gridWidth.hashCode() : 0);
      result = 31 * result + (gridHeight != null ? gridHeight.hashCode() : 0);
      result = 31 * result + (weightX != null ? weightX.hashCode() : 0);
      result = 31 * result + (weightY != null ? weightY.hashCode() : 0);
      result = 31 * result + (anchorX != null ? anchorX.hashCode() : 0);
      result = 31 * result + (anchorY != null ? anchorY.hashCode() : 0);
      result = 31 * result + (baseline != null ? baseline.hashCode() : 0);
      result = 31 * result + (insetTop != null ? insetTop.hashCode() : 0);
      result = 31 * result + (insetLeft != null ? insetLeft.hashCode() : 0);
      result = 31 * result + (insetBottom != null ? insetBottom.hashCode() : 0);
      result = 31 * result + (insetRight != null ? insetRight.hashCode() : 0);
      result = 31 * result + (iPadX != null ? iPadX.hashCode() : 0);
      result = 31 * result + (iPadY != null ? iPadY.hashCode() : 0);
      return result;
   }

   @Override
   public String toString()
   {
      return String.format("CellSpec{preferredSize=%s,%s gridSize=%s,%s weight=%s,%s anchorX=%s anchorY=%s baseline=%b" +
                              "insets(top=%s left=%s bottom=%s right=%s) pad=%s,%s}",
                           preferredWidth == null ? WILDCARD : preferredWidth,
                           preferredHeight == null ? WILDCARD : preferredHeight,
                           gridWidth == null ? WILDCARD : gridSizeToString(gridWidth),
                           gridHeight == null ? WILDCARD : gridSizeToString(gridHeight),
                           weightX == null ? WILDCARD : weightX,
                           weightY == null ? WILDCARD : weightY,
                           anchorX == null ? WILDCARD : anchorX,
                           anchorY == null ? WILDCARD : anchorY,
                           baseline == null ? WILDCARD : baseline,
                           insetTop == null ? WILDCARD : insetTop,
                           insetLeft == null ? WILDCARD : insetLeft,
                           insetBottom == null ? WILDCARD : insetBottom,
                           insetRight == null ? WILDCARD : insetRight,
                           iPadX == null ? WILDCARD : iPadX,
                           iPadY == null ? WILDCARD : iPadY);
   }

}
