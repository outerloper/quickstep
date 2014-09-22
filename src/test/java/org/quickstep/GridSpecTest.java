package org.quickstep;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.quickstep.GridBagToolKit.*;

public class GridSpecTest
{
   private GridSpec gridSpec = new GridSpec();

   @Test
   public void returnEmptySpecForNonSpecifiedCell()
   {
      assertEquals(spec(), gridSpec.getSpecAt(4, 4));
   }

   @Test
   public void whenCellSpecifiedThenReturnItsSpec()
   {
      gridSpec
         .withCell(1, 1, spec().withInsetTop(1))
         .withColumn(2, spec().withInsetTop(2))
         .withRow(2, spec().withInsetTop(3));
      assertEquals(spec().withInsetTop(1), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInsetTop(2), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInsetTop(3), gridSpec.getSpecAt(1, 2));
      assertEquals(spec(), gridSpec.getSpecAt(3, 3));
   }

   @Test
   public void specifyCellMoreThanOnceOverridesPreviousSpec()
   {
      gridSpec
         .withCell(1, 1, spec().withInset(1))
         .withCell(1, 1, spec().withInsetTop(10))
         .withColumn(2, spec().withInset(2))
         .withColumn(2, spec().withInsetTop(20))
         .withRow(2, spec().withInset(3))
         .withRow(2, spec().withInsetTop(30));
      assertEquals(spec().withInset(1).withInsetTop(10), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInset(2).withInsetTop(20), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInset(3).withInsetTop(30), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenDefaultSpecifiedThenReturnDefaultSpecForNonSpecifiedCell()
   {
      gridSpec
         .withDefault(spec().withGridSize(2, 3));
      assertEquals(spec().withGridSize(2, 3), gridSpec.getSpecAt(2, 2));
   }

   @Test
   public void whenDefaultSpecifiedAndParticularCellSpecifiedThenReturnOverriddenDefaultSpec()
   {
      gridSpec
         .withDefault(spec().withInset(3))
         .withCell(1, 1, spec().withInsetTop(1))
         .withColumn(2, spec().withInsetTop(2))
         .withRow(2, spec().withInsetTop(3));
      assertEquals(spec().withInset(3).withInsetTop(1), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInset(3).withInsetTop(2), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInset(3).withInsetTop(3), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void specifyDefaultMoreThanOnceOverridesPreviousDefault()
   {
      gridSpec
         .withDefault(spec().withInset(3))
         .withDefault(spec().withInsetTop(6));
      assertEquals(spec().withInset(3).withInsetTop(6), gridSpec.getSpecAt(2, 2));
   }

   @Test
   public void whenRowAndColumnSpecifiedThenCellForThisRowAndColumnInheritsSpecFromBoth()
   {
      gridSpec
         .withColumn(1, spec().withInsetBottom(20))
         .withRow(2, spec().withInsetTop(10));
      assertEquals(spec().withInsetTop(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenColumnAndThenRowSpecifiedThenColumnOverridesRow()
   {
      gridSpec
         .withColumn(1, spec().withInset(10))
         .withRow(2, spec().withInsetBottom(20));
      assertEquals(spec().withInset(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenRowAndThenColumnSpecifiedThenColumnOverridesRow()
   {
      gridSpec
         .withRow(2, spec().withInset(10))
         .withColumn(1, spec().withInsetBottom(20));
      assertEquals(spec().withInset(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenRowSpecifiedWithLineSpecThenGridRowAndItsCellsAreOverriddenWithLineSpecs()
   {
      gridSpec
         .withRow(1, lineSpec()
                     .withDefault(spec().withInset(10))
                     .withCell(1, spec().withInsetX(5))
         );
      assertEquals(spec(), gridSpec.getSpecAt(0, 0));
      assertEquals(spec(), gridSpec.getSpecAt(1, 0));
      assertEquals(spec().withInset(10), gridSpec.getSpecAt(0, 1));
      assertEquals(spec().withInset(10).withInsetX(5), gridSpec.getSpecAt(1, 1));
   }

   @Test
   public void whenColumnSpecifiedWithLineSpecThenGridColumnAndItsCellsAreOverriddenWithLineSpecs()
   {
      gridSpec
         .withColumn(1, lineSpec()
                        .withDefault(spec().withInset(10))
                        .withCell(1, spec().withInsetX(5))
         );
      assertEquals(spec(), gridSpec.getSpecAt(0, 0));
      assertEquals(spec(), gridSpec.getSpecAt(0, 1));
      assertEquals(spec().withInset(10), gridSpec.getSpecAt(1, 0));
      assertEquals(spec().withInset(10).withInsetX(5), gridSpec.getSpecAt(1, 1));
   }

   @Test
   public void equals()
   {
      gridSpec
         .withDefault(spec().withGap(5))
         .withColumn(1, spec().withIPad(10))
         .withRow(2, spec().withWeightX(0.6));
      GridSpec expected = gridSpec().withDefault(spec().withGap(5))
         .withColumn(1, spec().withIPad(10))
         .withRow(2, spec().withWeightX(0.6));
      assertEquals(expected, gridSpec);
   }

   @Test
   public void specsAreNotEqualWhenDefaultOrLinesOrCellsAreSpecifiedDifferentlyEvenWhenForEachCellSpecReturnsTheSameSpec()
   {
      gridSpec
         .withDefault(spec().withGap(5))
         .withColumn(1, spec().withIPad(10))
         .withRow(2, spec().withWeightX(0.6));
      GridSpec expected = gridSpec().withDefault(spec().withGap(5))
         .withColumn(1, spec().withIPad(10))
         .withRow(2, spec().withWeightX(0.6))
         .withCell(1, 2, spec().withIPad(10));
      assertFalse(expected.equals(gridSpec));
   }

   @Test
   public void whenOverridingLineSpecThenDefaultsAndLinesAndCellsAreOverridden()
   {
      gridSpec
         .withDefault(spec().withIPadX(5))
         .withCell(1, 3, specWithFillX());
      GridSpec overriding = gridSpec()
         .withDefault(spec().withIPadY(5))
         .withCell(1, 3, specWithFillY())
         .withRow(2, specWithFillY());
      gridSpec.overrideWith(overriding);
      GridSpec expected = gridSpec()
         .withDefault(spec().withIPad(5))
         .withCell(1, 3, specWithFill())
         .withRow(2, specWithFillY());
      assertEquals(expected, gridSpec.overrideWith(overriding));
   }

   @Test
   public void whenOverridingGridSpecWithNullThenEqualSpecIsReturned()
   {
      gridSpec
         .withDefault(spec().withIPadX(5))
         .withCell(1, 3, specWithFillX())
         .withRow(2, specWithFillY());
      GridSpec expected = gridSpec()
         .withDefault(spec().withIPadX(5))
         .withCell(1, 3, specWithFillX())
         .withRow(2, specWithFillY());
      assertEquals(expected, gridSpec.overrideWith(null));
   }
}
