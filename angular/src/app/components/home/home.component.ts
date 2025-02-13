import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Product } from 'src/app/product';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  public products: Product[];
  constructor(private productService : ProductService, private authService: AuthService, private router: Router){}

  isAuthenticated: boolean;

  ngOnInit() {

    this.isAuthenticated = this.authService.isLoggedIn();
    
    if(!(localStorage.getItem('authToken'))){
      this.productService.getRecommendedProductsWithoutToken().subscribe(products => {
        this.products = products;
    });

    }
    else{
      this.getRecommendedProducts();
    }
    
  }

  public getRecommendedProducts(): void {
    this.productService.getRecommendedProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }


  goToProduct(event: Event, product: any) {
    event.preventDefault();
    
  
    if (this.isAuthenticated) {
      this.router.navigate(['/product', product.id]);

    } else {
      alert("Please login to use the full benefits of the ShopSmart RRS!")
      this.router.navigate(['/login']);
    }

  }

  

}
