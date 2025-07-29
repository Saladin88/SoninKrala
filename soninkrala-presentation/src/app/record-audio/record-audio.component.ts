import { CommonModule } from '@angular/common';
import {Component, NgZone, inject, OnDestroy} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PronunciationService } from './service/pronunciation.service';
import { ResultPronunciation } from './Model/pronunciations';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-record-audio',
  imports: [CommonModule, MatIconModule, ReactiveFormsModule, MatButtonModule ],
  templateUrl: './record-audio.component.html',
  styleUrls: ['./record-audio.component.css']
})
export class RecordAudioComponent implements OnDestroy {


  resultPronunciation : ResultPronunciation | null = null;
  audioChunks : Blob[] = [];
  recordedAudioBlob! : Blob;
  mediaRecorder! : MediaRecorder;
  timer! : number;
  recorderStatus : RecordingState = 'inactive';
  audioUrl: string = "";
  analyser! : AnalyserNode;
  zone = inject(NgZone);
  pronunciationService = inject(PronunciationService);
  private readonly subscriptions: Subscription[] = [];

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
          this.recordedAudioBlob = audioBlob;
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

  sendRecord(event : MouseEvent) {
    event.preventDefault()
    if( event.isTrusted && this.recordedAudioBlob) {
      console.log('Audio chunks before sending:', this.audioChunks);
      console.log('Audio URL:', this.audioUrl);
      const audioFile = new File([this.recordedAudioBlob], 'audio.webm', { type: 'audio/webm' });
      const recordSub = this.pronunciationService.sendVoicePronunciation(this.createFormData(audioFile)).subscribe({
        next: (response : ResultPronunciation) => {
          console.log('Audio sent successfully:', response);
          this.audioUrl = '';
          this.resultPronunciation = response;
        },
        error: (error) => {
          console.error('Error sending audio:', error);
        }
      })
      this.subscriptions.push(recordSub);
    }
  }

  createFormData(audioFile : File) : FormData {
    const formData = new FormData();
    formData.append('audioFile', audioFile, audioFile.name);
    formData.append('audioFileName', audioFile.name);
    console.log('this is formdata = ',formData );
    console.log('Contenu du FormData :');
for (const pair of formData.entries()) {
  console.log(`${pair[0]}:`, pair[1]);
}

    return formData
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
