import {Component} from '@angular/core';
import { Router } from '@angular/router';
import {trigger, style, animate, transition} from '@angular/animations';

import {User} from '../../../../model/user.model';
import {UserService} from "../../../../service/user.service";

@Component({
  selector: 'modal-profile-edit',
  templateUrl: 'modal-profile-edit.html',
  styles: [`
    .hide {
      display: none
    }`]
  ,
  animations: [
    trigger('dialog', [
      transition('void => *', [
        animate(100, style({transform: 'scale3d(.3, .3, .3)'}))
      ]),
      transition('* => void', [
        animate(100, style({transform: 'scale3d(.0, .0, .0)'}))
      ])
    ])
  ]
})
export class ModalProfileEdit {
  visible: boolean;
  user: User;
  userImage: any;

  constructor(private userService: UserService, private router: Router) {
    this.visible = false;
  }

  edit(firstName, lastName1, lastName2, email, telephone, viewTelephone, address) {
    let updatedUser = {
      id: this.user.id, name: this.user.name, dni: this.user.dni, firstName: firstName,
      lastName1: lastName1, lastName2: lastName2, email: email, telephone: telephone,
      viewTelephone: viewTelephone, address: address, biography: this.user.biography
    };

    this.userService.updateUser(updatedUser, true).subscribe(
      response => {
        if (this.userImage !== undefined) {
          console.log("Uploading file...");
          let formData = new FormData();
          formData.append('file', this.userImage, this.userImage.name);
          this.userService.updateFile(formData, updatedUser).subscribe();
        }
        console.log(this.user.name + " successfully updated.");
        this.user = this.userService.getUserCompleted();
        this.router.navigate(['/profile']);
        this.close();
      },
      error => console.log("Fail trying to modify " + this.user.name + ".")
    );
  }

  selectFile($event) {
    this.userImage = $event.target.files[0];
    console.log("Selected file: " + this.userImage.name + " type:" + this.userImage.type + " size:" + this.userImage.size);
  }

  close(): void {
    this.visible = false;
  }

  open(user: User): void {
    if (!this.visible) {
      this.visible = true;
    }
    this.user = user;
  }
}
