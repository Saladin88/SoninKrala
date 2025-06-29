import { CommonModule } from '@angular/common';
import { Component, NgZone, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';


@Component({
  selector: 'app-record-audio',
  imports: [CommonModule, MatIconModule, ReactiveFormsModule, MatButtonModule ],
  templateUrl: './record-audio.component.html',
  styleUrls: ['./record-audio.component.css']
})
export class RecordAudioComponent {


  audioChunks : Blob[] = [];
  mediaRecorder! : MediaRecorder;
  timer! : number;
  recorderStatus : RecordingState = 'inactive';
  audioUrl: string = "";
  analyser! : AnalyserNode;
  zone = inject(NgZone);

  audioSubmit =  new FormControl(File, [Validators.required]);

  async startRecording() {
    try {
      this.audioChunks = [];
      const stream = await navigator.mediaDevices.getUserMedia({audio : true})
      this.mediaRecorder = new MediaRecorder(stream);

      this.mediaRecorder.ondataavailable = event => {
        console.log(event);
        console.log('this is the chunk = ', this.audioChunks)
        this.audioChunks.push(event.data)
        console.log('this is the chunk after data push = ', this.audioChunks)
      }

      this.mediaRecorder.onstop = () => {
        this.zone.run(()=> {
          const audioBlob = new Blob(this.audioChunks, {type : 'audio/webm'});
          this.audioUrl = URL.createObjectURL(audioBlob);
          this.recorderStatus = 'inactive';
          this.audioChunks = [];
        })
      };
      this.mediaRecorder.start();
      this.recorderStatus = 'recording'
    } catch (err) {
      console.error(err)
    }

  }

  stopRecording() {
    if(this.mediaRecorder && this.recorderStatus !== 'inactive') {
      this.mediaRecorder.stop();
    }
  }

  suspendRecording() {
    if(!this.mediaRecorder) {
      console.warn('Media not yet started');
      return;
    }
    if(this.recorderStatus === 'recording') {
      this.mediaRecorder.pause();
      this.recorderStatus = 'paused';
    } else if (this.recorderStatus === 'paused') {
      this.mediaRecorder.resume();
      this.recorderStatus = 'recording'

    }

  }

  onSubmit(event : any) {
    event.preventDefault()
  }
}
