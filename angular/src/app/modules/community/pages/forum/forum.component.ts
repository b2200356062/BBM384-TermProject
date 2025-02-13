import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddPostComponent } from '../../components/post/add-post/add-post.component';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post.service';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {
  postsSubscription: Subscription | undefined
  posts: Post[] = [
    {id:1, title: "adress information and phone number in my account is not the mine", content: "Good evening! I wanted to open a new account but the system tells me that I already have an account with Paypal with my email address that I provided.Plus, when I logged in, there is a phone number and address I don't know on the account.Additionally...", likesCount: 100, commentsCount: 20, viewsCount: 120 },
    {id:2, title: "adress information and phone number in my account is not the mine", content: "Good evening! I wanted to open a new account but the system tells me that I already have an account with Paypal with my email address that I provided.Plus, when I logged in, there is a phone number and address I don't know on the account.Additionally...", likesCount: 100, commentsCount: 20, viewsCount: 120 },
    {id:3, title: "adress information and phone number in my account is not the mine", content: "Good evening! I wanted to open a new account but the system tells me that I already have an account with Paypal with my email address that I provided.Plus, when I logged in, there is a phone number and address I don't know on the account.Additionally...", likesCount: 100, commentsCount: 20, viewsCount: 120 }

  ]

  constructor(public dialog: MatDialog,private postService:PostService) { }
  ngOnInit(): void {
    this.getPosts()
  }

  getPosts(): void {
    this.postsSubscription = this.postService.getPosts().subscribe((posts) => {
      this.posts = posts;
    });
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(AddPostComponent, {
      width: '500px',
      height: '500px',
      data: { name: 'Angular' }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed. Received data:', result);
      this.getPosts()
    });
  }

  ngOnDestroy(): void {
    this.postsSubscription?.unsubscribe()
  }
}
