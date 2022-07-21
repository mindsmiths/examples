import csv

import click
from click import echo
from forge import setup
from forge_cli.admin import cli

from model_trainer.models import DataPoint


@cli.command()
@click.argument('size', default=115)
@click.argument('dataset', default='bmi_data.csv')
def import_data(dataset: str, size: int) -> None:
    setup("model_trainer")

    with open(f"services/model_trainer/{dataset}", newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        counter = 0

        data_points = []
        for row in reader:
            counter += 1
            data_points.append(
                DataPoint(inputs={"age": int(row['age']), "bmi": float(row['bmi'])}, result=row['result'],
                          modelId="bmi-model").dict()
            )
            if counter >= size:
                break

        DataPoint.get_collection().insert_many(data_points)
        echo(f'Imported {size} datapoints')
