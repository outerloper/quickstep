package org.quickstep;

import java.awt.*;

import static org.quickstep.GridBagToolKit.*;

public final class GridBagSpec
{
   private Integer preferredWidth;
   private Integer preferredHeight;
   private Integer gridWidth;
   private Integer gridHeight;
   private Double weightX;
   private Double weightY;
   private AnchorX anchorX;
   private AnchorY anchorY;
   private Boolean fillX;
   private Boolean fillY;
   private Integer insetTop;
   private Integer insetLeft;
   private Integer insetBottom;
   private Integer insetRight;
   private Integer iPadX;
   private Integer iPadY;

   GridBagSpec()
   {
   }

   GridBagSpec(GridBagSpec that)
   {
      this(that.preferredWidth,
           that.preferredHeight,
           that.gridWidth,
           that.gridHeight,
           that.weightX,
           that.weightY,
           that.anchorX,
           that.anchorY,
           that.fillX,
           that.fillY,
           that.insetTop,
           that.insetLeft,
           that.insetBottom,
           that.insetRight,
           that.iPadX,
           that.iPadY
      );
   }

   public GridBagSpec(Integer preferredWidth,
                      Integer preferredHeight,
                      Integer gridWidth,
                      Integer gridHeight,
                      Double weightX,
                      Double weightY,
                      AnchorX AnchorX,
                      AnchorY AnchorY,
                      Boolean fillX,
                      Boolean fillY,
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
      this.anchorX = AnchorX;
      this.anchorY = AnchorY;
      this.fillX = fillX;
      this.fillY = fillY;
      this.insetTop = insetTop;
      this.insetLeft = insetLeft;
      this.insetBottom = insetBottom;
      this.insetRight = insetRight;
      this.iPadX = iPadX;
      this.iPadY = iPadY;
   }

   public GridBagSpec overwriteWith(GridBagSpec that)
   {
      preferredWidth = that.preferredWidth;
      preferredHeight = that.preferredHeight;
      gridWidth = that.gridWidth;
      gridHeight = that.gridHeight;
      weightX = that.weightX;
      weightY = that.weightY;
      anchorX = that.anchorX;
      anchorY = that.anchorY;
      fillX = that.fillX;
      fillY = that.fillY;
      insetTop = that.insetTop;
      insetLeft = that.insetLeft;
      insetBottom = that.insetBottom;
      insetRight = that.insetRight;
      iPadX = that.iPadX;
      iPadY = that.iPadY;
      return this;
   }

   public GridBagSpec derive()
   {
      return new GridBagSpec(this);
   }

   @SuppressWarnings("ConstantConditions")
   public GridBagSpec overrideWith(GridBagSpec that)
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
      if (that.fillX != null)
      {
         fillX = that.fillX;
      }
      if (that.fillY != null)
      {
         fillY = that.fillY;
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

   public AnchorX getAnchorX()
   {
      return anchorX;
   }

   public AnchorY getAnchorY()
   {
      return anchorY;
   }

   public Boolean getFillX()
   {
      return fillX;
   }

   public Boolean getFillY()
   {
      return fillY;
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


   public GridBagSpec withPreferredWidth(Integer value)
   {
      preferredWidth = value;
      return this;
   }

   public GridBagSpec withPreferredHeight(Integer value)
   {
      preferredHeight = value;
      return this;
   }

   public GridBagSpec withPreferredSize(Integer width, Integer height)
   {
      return withPreferredWidth(width).withPreferredHeight(height);
   }

   public GridBagSpec withGridWidth(Integer value)
   {
      gridWidth = value;
      return this;
   }

   public GridBagSpec withGridHeight(Integer value)
   {
      gridHeight = value;
      return this;
   }

   public GridBagSpec withGridWidthRemaining()
   {
      return withGridWidth(GridBagConstraints.REMAINDER);
   }

   public GridBagSpec withGridHeightRemaining()
   {
      return withGridHeight(GridBagConstraints.REMAINDER);
   }

   public GridBagSpec withGridSize(Integer width, Integer height)
   {
      return withGridWidth(width).withGridHeight(height);
   }

   public GridBagSpec withWeightX(Double value)
   {
      weightX = value;
      return this;
   }

   public GridBagSpec withWeightY(Double value)
   {
      weightY = value;
      return this;
   }

   public GridBagSpec withWeight(Double x, Double y)
   {
      return withWeightX(x).withWeightY(y);
   }

   public GridBagSpec withWeight(Double value)
   {
      return withWeight(value, value);
   }

   public GridBagSpec withAnchorX(AnchorX value)
   {
      anchorX = value;
      return this;
   }

   public GridBagSpec withAnchorY(AnchorY value)
   {
      anchorY = value;
      return this;
   }

   public GridBagSpec withAnchor(AnchorX x, AnchorY y)
   {
      return withAnchorX(x).withAnchorY(y);
   }

   public GridBagSpec withFillX(Boolean value)
   {
      fillX = value;
      return this;
   }

   public GridBagSpec withFillY(Boolean value)
   {
      fillY = value;
      return this;
   }

   public GridBagSpec withFill(Boolean value)
   {
      return withFillX(value).withFillY(value);
   }

   public GridBagSpec withFillX()
   {
      return withFillX(true);
   }

   public GridBagSpec withFillY()
   {
      return withFillY(true);
   }

   public GridBagSpec withFill()
   {
      return withFill(true);
   }

   public GridBagSpec withInsetTop(Integer value)
   {
      insetTop = value;
      return this;
   }

   public GridBagSpec withInsetLeft(Integer value)
   {
      insetLeft = value;
      return this;
   }

   public GridBagSpec withInsetBottom(Integer value)
   {
      insetBottom = value;
      return this;
   }

   public GridBagSpec withInsetRight(Integer value)
   {
      insetRight = value;
      return this;
   }

   public GridBagSpec withInsetX(Integer value)
   {
      return withInsetLeft(value).withInsetRight(value);
   }

   public GridBagSpec withInsetY(Integer value)
   {
      return withInsetTop(value).withInsetBottom(value);
   }

   public GridBagSpec withInset(Integer value)
   {
      return withInset(value, value, value, value);
   }

   public GridBagSpec withInset(Integer x, Integer y)
   {
      return withInset(y, x, y, x);
   }

   public GridBagSpec withGapX(Integer value)
   {
      return withInsetLeft(value);
   }

   public GridBagSpec withGapY(Integer value)
   {
      return withInsetTop(value);
   }

   public GridBagSpec withGap(Integer x, Integer y)
   {
      return withGapX(x).withGapY(y);
   }

   public GridBagSpec withGap(Integer value)
   {
      return withGap(value, value);
   }

   public GridBagSpec withInset(Integer top, Integer left, Integer bottom, Integer right)
   {
      return withInsetTop(top).withInsetLeft(left).withInsetBottom(bottom).withInsetRight(right);
   }

   public GridBagSpec withIPadX(Integer value)
   {
      iPadX = value;
      return this;
   }

   public GridBagSpec withIPadY(Integer value)
   {
      iPadY = value;
      return this;
   }

   public GridBagSpec withIPad(Integer x, Integer y)
   {
      return withIPadX(x).withIPadY(y);
   }

   public GridBagSpec withIPad(Integer value)
   {
      return withIPad(value, value);
   }


   public GridBagConstraints toConstraints(int x, int y)
   {
      GridBagSpec spec = completeSpec().overrideWith(this);
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

   public int getAnchor() // TODO getAnchor/getAlignment
   {
      AnchorX x = anchorX == null ? AnchorX.CENTER : anchorX;
      AnchorY y = anchorY == null ? AnchorY.MIDDLE : anchorY;
      switch (y)
      {
         case TOP:
            switch (x)
            {
               case LEFT:
                  return GridBagConstraints.NORTHWEST;
               case RIGHT:
                  return GridBagConstraints.NORTHEAST;
            }
            return GridBagConstraints.NORTH;
         case BOTTOM:
            switch (x)
            {
               case LEFT:
                  return GridBagConstraints.SOUTHWEST;
               case RIGHT:
                  return GridBagConstraints.SOUTHEAST;
            }
            return GridBagConstraints.SOUTH;
      }
      switch (x)
      {
         case LEFT:
            return GridBagConstraints.WEST;
         case RIGHT:
            return GridBagConstraints.EAST;
      }
      return GridBagConstraints.CENTER;
   }

   public int getFill()
   {
      Boolean x = fillX == null ? false : fillX;
      Boolean y = fillY == null ? false : fillY;
      if (x)
      {
         if (y)
         {
            return GridBagConstraints.BOTH;
         }
         return GridBagConstraints.HORIZONTAL;
      }
      if (y)
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

      GridBagSpec that = (GridBagSpec) o;

      if (anchorX != that.anchorX)
      {
         return false;
      }
      if (anchorY != that.anchorY)
      {
         return false;
      }
      if (fillX != that.fillX)
      {
         return false;
      }
      if (fillY != that.fillY)
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
      result = 31 * result + (fillX != null ? fillX.hashCode() : 0);
      result = 31 * result + (fillY != null ? fillY.hashCode() : 0);
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
      return String.format("GBSpec{preferredSize=%s,%s gridSize=%s,%s weight=%s,%s fillX=%s fillY=%s AnchorX=%s AnchorY=%s " +
                              "insets(top=%s left=%s bottom=%s right=%s) pad=%s,%s}",
                           preferredWidth, preferredHeight, gridWidth, gridHeight, weightX, weightY, fillX, fillY, anchorX, anchorY,
                           insetTop, insetLeft, insetBottom, insetRight, iPadX, iPadY);
   }
}
