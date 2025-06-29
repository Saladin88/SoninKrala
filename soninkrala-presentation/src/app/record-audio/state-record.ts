type RecorderState = 'ready' | 'ready.countdown' | 'recording';

export class AudioRecordState {
    state!: RecorderState;
}