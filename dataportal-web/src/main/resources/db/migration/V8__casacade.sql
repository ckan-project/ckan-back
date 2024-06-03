alter table hanyang.resource drop foreign key resource_ibfk_1;

alter table hanyang.resource
add constraint fk_dataset
foreign key (dataset_id) references hanyang.dataset(dataset_id)
on delete cascade;

alter table hanyang.scrap drop foreign key scrap_ibfk_1;

alter table hanyang.scrap
add constraint fk_dataset
foreign key (dataset_id) references hanyang.dataset(dataset_id)
on delete cascade;

alter table hanyang.download drop foreign key download_ibfk_1;

alter table hanyang.download
add constraint fk_dataset
foreign key (dataset_id) references hanyang.dataset(dataset_id)
on delete cascade;