# --- !Ups
alter table public.usuario add column email varchar(255);
alter table public.usuario add column senha varchar(255);
# --- !Downs
alter table public.usuario drop column email;
alter table public.usuario drop column senha;