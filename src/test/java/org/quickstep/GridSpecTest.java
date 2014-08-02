package org.quickstep;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
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
         .specifyCell(1, 1, spec().withInsetTop(1))
         .specifyColumn(2, spec().withInsetTop(2))
         .specifyRow(2, spec().withInsetTop(3));
      assertEquals(spec().withInsetTop(1), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInsetTop(2), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInsetTop(3), gridSpec.getSpecAt(1, 2));
      assertEquals(spec(), gridSpec.getSpecAt(3, 3));
   }

   @Test
   public void specifyCellMoreThanOnceOverridesPreviousSpec()
   {
      gridSpec
         .specifyCell(1, 1, spec().withInset(1))
         .specifyCell(1, 1, spec().withInsetTop(10))
         .specifyColumn(2, spec().withInset(2))
         .specifyColumn(2, spec().withInsetTop(20))
         .specifyRow(2, spec().withInset(3))
         .specifyRow(2, spec().withInsetTop(30));
      assertEquals(spec().withInset(1).withInsetTop(10), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInset(2).withInsetTop(20), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInset(3).withInsetTop(30), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenDefaultSpecifiedThenReturnDefaultSpecForNonSpecifiedCell()
   {
      gridSpec
         .specifyDefault(spec().withGridSize(2, 3));
      assertEquals(spec().withGridSize(2, 3), gridSpec.getSpecAt(2, 2));
   }

   @Test
   public void whenDefaultSpecifiedAndParticularCellSpecifiedThenReturnOverriddenDefaultSpec()
   {
      gridSpec
         .specifyDefault(spec().withInset(3))
         .specifyCell(1, 1, spec().withInsetTop(1))
         .specifyColumn(2, spec().withInsetTop(2))
         .specifyRow(2, spec().withInsetTop(3));
      assertEquals(spec().withInset(3).withInsetTop(1), gridSpec.getSpecAt(1, 1));
      assertEquals(spec().withInset(3).withInsetTop(2), gridSpec.getSpecAt(2, 1));
      assertEquals(spec().withInset(3).withInsetTop(3), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void specifyDefaultMoreThanOnceOverridesPreviousDefault() {
      gridSpec
         .specifyDefault(spec().withInset(3))
         .specifyDefault(spec().withInsetTop(6));
      assertEquals(spec().withInset(3).withInsetTop(6), gridSpec.getSpecAt(2, 2));
   }

   @Test
   public void whenRowAndColumnSpecifiedThenCellForThisRowAndColumnInheritsSpecFromBoth() {
      gridSpec
         .specifyColumn(1, spec().withInsetBottom(20))
         .specifyRow(2, spec().withInsetTop(10));
      assertEquals(spec().withInsetTop(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenColumnAndThenRowSpecifiedThenColumnOverridesRow() {
      gridSpec
         .specifyColumn(1, spec().withInset(10))
         .specifyRow(2, spec().withInsetBottom(20));
      assertEquals(spec().withInset(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }

   @Test
   public void whenRowAndThenColumnSpecifiedThenColumnOverridesRow() {
      gridSpec
         .specifyRow(2, spec().withInset(10))
         .specifyColumn(1, spec().withInsetBottom(20));
      assertEquals(spec().withInset(10).withInsetBottom(20), gridSpec.getSpecAt(1, 2));
   }
}
