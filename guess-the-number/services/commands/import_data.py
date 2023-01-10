import csv
from time import sleep

import click
from click import echo
from forge import setup
from forge.core.api.base import Event
from forge_cli.admin import cli


@cli.command()
@click.argument('dataset', default='game_data.csv')
def import_data(dataset: str) -> None:
    setup("cli")

    with open(f"services/commands/{dataset}", newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        for row in reader:
            Celebrity(
                name=row['name'],
                followerCount=int(row['follower_count']),
                description=row['description'],
                imageUrl=row['url']
            ).emit()

    sleep(1)
    echo('Imported celebrities')


class Celebrity(Event):
    name: str
    followerCount: int
    description: str
    imageUrl: str
