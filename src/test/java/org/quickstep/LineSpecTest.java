package org.quickstep;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.quickstep.GridBagToolKit.*;

public class LineSpecTest
{
   private LineSpec lineSpec = new LineSpec();

   @Test
   public void returnEmptySpecForNonSpecifiedCell()
   {
      assertEquals(spec(), lineSpec.getSpecAt(4));
   }

   @Test
   public void whenCellSpecifiedThenReturnItsSpec()
   {
      lineSpec
         .specifyCell(2, spec().withInset(3));
      assertEquals(spec().withInset(3), lineSpec.getSpecAt(2));
   }

   @Test
   public void specifyCellMoreThanOnceOverridesPreviousSpec()
   {
      lineSpec
         .specifyCell(2, spec().withInset(3))
         .specifyCell(2, spec().withInsetTop(6));
      assertEquals(spec().withInset(3).withInsetTop(6), lineSpec.getSpecAt(2));
   }

   @Test
   public void whenDefaultSpecifiedThenReturnDefaultSpecForNonSpecifiedCell()
   {
      lineSpec
         .specifyDefault(spec().withGridSize(2, 3));
      assertEquals(spec().withGridSize(2, 3), lineSpec.getSpecAt(2));
   }

   @Test
   public void whenDefaultSpecifiedAndParticularCellSpecifiedThenReturnOverriddenDefaultSpec()
   {
      lineSpec
         .specifyDefault(spec().withInset(3))
         .specifyCell(2, spec().withInsetTop(6));
      assertEquals(spec().withInset(3).withInsetTop(6), lineSpec.getSpecAt(2));
   }

   @Test
   public void specifyDefaultMoreThanOnceOverridesPreviousDefault() {
      lineSpec
         .specifyDefault(spec().withInset(3))
         .specifyDefault(spec().withInsetTop(6));
      assertEquals(spec().withInset(3).withInsetTop(6), lineSpec.getSpecAt(2));
   }
}
