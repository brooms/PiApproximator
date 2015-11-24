package au.com.brooms.dagger;

import au.com.brooms.approximation.Approximation;
import au.com.brooms.approximation.BaileyBorweinPlouffe;
import au.com.brooms.approximation.FabriceBellard;
import au.com.brooms.approximation.GregoryLeibniz;
import au.com.brooms.approximation.Madhava;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * Dagger DI framework main configuration module.
 *
 * @author Brooms
 * @version 0.1.0
 */
@Module
public class ApproximatorModule {

  @Provides
  @Named("Leibniz")
  public Approximation provideGregoryLeibniz() {
    return new GregoryLeibniz();
  }

  @Provides
  @Named("Madhava")
  public Approximation provideMadhava() {
    return new Madhava();
  }

  @Provides
  @Named("Fabrice")
  public Approximation provideFabrice() {
    return new FabriceBellard();
  }

  @Provides
  @Named("BaileyBorweinPlouffe")
  public Approximation provideBbp() {
    return new BaileyBorweinPlouffe();
  }

}
