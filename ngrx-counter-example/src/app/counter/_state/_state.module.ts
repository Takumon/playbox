import { NgModule, Optional, SkipSelf } from '@angular/core';
import { StoreModule } from '@ngrx/store';

import { reducers } from './_state.reducer';

@NgModule({
  imports: [
    StoreModule.forFeature('counter', reducers),
  ]
})
export class CounterStateModule {
  /**
   * Constructor
   * @param parentModule
   */
  constructor(@Optional() @SkipSelf() parentModule: CounterStateModule) {
    if (parentModule) {
      throw new Error('CounterStateModule is already loaded.');
    }
  }
}
