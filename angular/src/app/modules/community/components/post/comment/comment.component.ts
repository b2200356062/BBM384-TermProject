import { CommentService } from '../../../services/comment.service';
import { Comment } from './../../../models/Comment';
import { Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment!: Comment;
  @Output() commentDeleted = new EventEmitter<number>();

  edit:boolean=false;
  showButtons:boolean=false;

  constructor(private commentService:CommentService){
  }

  likeOrDislikeComment(action:string){
    if(this.comment.id) this.commentService.likeOrDislikeComment(action,this.comment.id).subscribe((comment)=>{
        this.comment=comment
    })
  }
  deleteComment(){
    if(this.comment.id) this.commentService.deleteComment(this.comment.id).subscribe((res)=>  this.commentDeleted.emit(this.comment.id))
  }
  updateComment(){
    this.commentService.updateComment(this.comment).subscribe((res)=>this.edit=false)
  }
  isEditing(){
    this.edit=true;
  }

  setShowButtons(value:boolean){
    this.showButtons=value;
  }
 
}
