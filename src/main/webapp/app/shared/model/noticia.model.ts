export interface INoticia {
  id?: number;
  titulo?: string | null;
  conteudo?: string | null;
  dataPublicacao?: Date | null;
  autor?: string | null;
  categoria?: string | null;
  fonte?: string | null;
  url?: string | null;
  classificacao?: string | null;
}

export class Noticia implements INoticia {
  constructor(
    public id?: number,
    public titulo?: string | null,
    public conteudo?: string | null,
    public dataPublicacao?: Date | null,
    public autor?: string | null,
    public categoria?: string | null,
    public fonte?: string | null,
    public url?: string | null,
    public classificacao?: string | null,
  ) {}
}
