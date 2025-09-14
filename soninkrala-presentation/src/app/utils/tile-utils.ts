export type TileType = 'quiz' | 'account' | 'pronunciation';

export class Tile {
  constructor(
    public type: TileType,
    public cols: number,
    public rows: number,
    public color?: string,
    public title?: string
  ) {}
}
